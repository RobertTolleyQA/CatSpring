package com.qa.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.qa.demo.persistence.domain.CatDomain;
import com.qa.demo.persistence.dtos.CatDTO;
import com.qa.demo.persistence.repo.CatRepo;

@Service
public class CatServices {

	private CatRepo repo;
	private ModelMapper mapper;

	@Autowired
	public CatServices(CatRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private CatDTO mapToDTO(CatDomain cat) {
		return this.mapper.map(cat, CatDTO.class);
	}


	@GetMapping("/read")
	public List<CatDTO> readAll() {
		List<CatDomain> dblist = this.repo.findAll();
		List<CatDTO> resultList = dblist.stream().map(this::mapToDTO).collect(Collectors.toList());
		
//		[1 , 2, 3, 4, 5]
//		
//		dblist[0]
		return resultList;
	}

	@GetMapping("/readOne/{id}")
	public CatDTO readOne(Long id) {
		return mapToDTO(this.repo.findById(id).orElseThrow());
		
	}
	
//	POST

	@PostMapping("/create")
	public CatDTO create(CatDomain cat) {
		return this.mapToDTO(this.repo.save(cat));

	}
	
//	

	@PutMapping("/update")
	public CatDTO update(Long id, CatDomain newDetails) {
		this.repo.findById(id).orElseThrow();
		
//		Cat target
		newDetails.setId(id);
		CatDTO result = mapToDTO(this.repo.save(newDetails));
		
		return result;

	}

	@DeleteMapping("/delete/{id}")
	public boolean delete(Long id) {
		
		
		try {
		this.repo.deleteById(id);
		boolean exists = this.repo.existsById(id);

		return !exists;
		
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

}
