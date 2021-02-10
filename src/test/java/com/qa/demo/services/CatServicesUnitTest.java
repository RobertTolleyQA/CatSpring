package com.qa.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.demo.persistence.domain.CatDomain;
import com.qa.demo.persistence.dtos.CatDTO;
import com.qa.demo.persistence.repo.CatRepo;

@SpringBootTest
public class CatServicesUnitTest {

	@MockBean // @Mock
	private ModelMapper MockMapper;
	@MockBean // @Mock
	private CatRepo mockRepo;

	@Autowired // @injectMocks
	private CatServices service;

//	 CRUD tests

	@Test
	public void create() {

		CatDomain testCat = new CatDomain(1L, 1, "rob", 10, null);
		CatDTO testDTO = new CatDTO(1L, 1, "rob", 10);

		// Rules
		Mockito.when(this.mockRepo.save(Mockito.any(CatDomain.class))).thenReturn(testCat);
		Mockito.when(this.MockMapper.map(testCat, CatDTO.class)).thenReturn(testDTO);

		// Actions

		CatDTO result = this.service.create(testCat);

		// Assertions

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(testDTO);
		Assertions.assertThat(result).usingRecursiveComparison()
		.isEqualTo(testDTO);
		
		Mockito.verify(this.mockRepo, Mockito.times(1)).save(Mockito.any(CatDomain.class));
		Mockito.verify(this.MockMapper, Mockito.times(1)).map(testCat, CatDTO.class);
	}

	@Test
	public void readAll() {
		CatDomain testCat = new CatDomain(1L, 1, "rob", 10, null);
		List<CatDomain> testList =  new ArrayList<>();
		testList.add(testCat);
		
		CatDTO testDTO = new CatDTO(1L, 1, "rob", 10);
		List<CatDTO> testDTOList = new ArrayList<>();
		testDTOList.add(testDTO);

		// Rules
		Mockito.when(this.mockRepo.findAll() ).thenReturn(testList);
		Mockito.when(this.MockMapper.map(testCat, CatDTO.class)).thenReturn(testDTO);

		// Actions

		List<CatDTO> result = this.service.readAll();

		// Assertions

//		Assertions.assertThat(result).isEqualTo(testDTOList.forEach(x -> testDTOList(x)));
		// Below is the correct way
		Assertions.assertThat(result).usingRecursiveComparison()
		.isEqualTo(testDTOList);
		
//		Mockito.verify(this.mockRepo, Mockito.times(1)).findById(1L);

	}

	@Test
	public void readOne() {
		CatDomain testCat = new CatDomain(1L, 1, "rob", 10, null);
		CatDTO testDTO = this.MockMapper.map(testCat, CatDTO.class);

		// Rules
		Mockito.when(this.mockRepo.findById(testCat.getId())).thenReturn(Optional.of(testCat));

		// Actions

		CatDTO result = this.service.readOne(1L);

		// Assertions

		Assertions.assertThat(result).isEqualTo(testDTO);
		// Below is the correct way
		Assertions.assertThat(result).usingRecursiveComparison()
		.isEqualTo(this.MockMapper.map(testCat, CatDTO.class));
		
		Mockito.verify(this.mockRepo, Mockito.times(1)).findById(1L);
	}
}
