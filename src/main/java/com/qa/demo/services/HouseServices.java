package com.qa.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.qa.demo.persistence.domain.HouseDomain;
import com.qa.demo.persistence.dtos.HouseDTO;
import com.qa.demo.persistence.repo.HouseRepo;

@Service
public class HouseServices {

	private HouseRepo repo;
	private ModelMapper mapper;

	@Autowired
	public HouseServices(HouseRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private HouseDTO mapToDTO(HouseDomain house) {
		return this.mapper.map(house, HouseDTO.class);
	}

	@GetMapping("/read")
	public List<HouseDTO> readAll() {
		List<HouseDomain> dblist = this.repo.findAll();
		List<HouseDTO> resultList = dblist.stream().map(this::mapToDTO).collect(Collectors.toList());

		return resultList;
	}

	@GetMapping("/readOne/{id}")
	public HouseDTO readOne(Long id) {
		return mapToDTO(this.repo.findById(id).orElseThrow());

	}

//	POST

	@PostMapping("/create")
	public HouseDTO create(HouseDomain house) {
		return this.mapToDTO(this.repo.save(house));

	}

//	

	@PutMapping("/update")
	public HouseDTO update(Long id, HouseDomain newDetails) {
		this.repo.findById(id).orElseThrow();

//		House target
		newDetails.setId(id);
		HouseDTO result = mapToDTO(this.repo.save(newDetails));

		return result;

	}

	@DeleteMapping("/delete/{id}")
	public boolean delete(Long id) {

		this.repo.deleteById(id);
		boolean exists = this.repo.existsById(id);

		return !exists;
	}

}
