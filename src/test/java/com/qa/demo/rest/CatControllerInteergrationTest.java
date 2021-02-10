package com.qa.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.CatDomain;
import com.qa.demo.persistence.dtos.CatDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class CatControllerInteergrationTest {

	@Autowired
	private MockMvc mock;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private ObjectMapper jsonifier;

	private CatDTO mapToDTO(CatDomain model) {
		return this.mapper.map(model, CatDTO.class);
	}

	private final Long ID = 1L;

	@Test
	public void readAll() throws Exception {
		CatDTO exRes1 = new CatDTO(1L, 10, "Bobby", 11);
		CatDTO exRes2 = new CatDTO(2L, 4, "Robbie", 2);
		CatDTO exRes3 = new CatDTO(3L, 8, "Johnny", 8);
		List<CatDTO> expectedList = new ArrayList<>();
		expectedList.add(exRes1);
		expectedList.add(exRes2);
		expectedList.add(exRes3);

		// Request setup
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
				"http://localhost:8080/cats/readAll");

		// Expectations setup
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedList));

		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	@Test
	public void readOne() throws Exception {

		CatDTO expectedResult = new CatDTO(1L, 10, "Bobby", 11);

		// Setup the request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
				"http://localhost:8080/cats/readOne/" + ID);

		// Set up the expectations
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

//		Perform
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}

	@Test
	public void create() throws Exception {

//		Long id, int age, String name, int meowVolume, HouseDomain myHouse

		CatDomain contentBody = new CatDomain(2, "Bob", 8, null);
		CatDTO expectedResult = mapToDTO(contentBody);
		expectedResult.setId(4L);

		// Setup the request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.POST, "http://localhost:8080/cats/create").contentType(MediaType.APPLICATION_JSON)
				.content(jsonifier.writeValueAsString(contentBody)).accept(MediaType.APPLICATION_JSON);

		// Set up the expectations
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

//			Perform
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}

	@Test
	public void update() throws Exception {
		
		CatDomain contentBody = new CatDomain(1L, 4, "Bill", 5, null);
		CatDTO expectedResult = mapToDTO(contentBody);
		
		// Setup the request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.PUT, "http://localhost:8080/cats/update/" + ID).contentType(MediaType.APPLICATION_JSON)
				.content(jsonifier.writeValueAsString(contentBody)).accept(MediaType.APPLICATION_JSON);
		
		// Set up the expectations
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

//		Perform
	this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);


	}

	@Test
	public void delete() throws Exception {

		// Setup the request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.DELETE, "http://localhost:8080/cats/delete/" + ID);

		// Set up the expectations
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isNoContent();

//		Perform
		this.mock.perform(mockRequest).andExpect(matchStatus);

	}
	
	@Test
	public void deleteWrong() throws Exception {
		
		Long wrongID = 100L;

		// Setup the request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.DELETE, "http://localhost:8080/cats/delete/" + wrongID);

		// Set up the expectations
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isInternalServerError();

//		Perform
		this.mock.perform(mockRequest).andExpect(matchStatus);

	}
}
