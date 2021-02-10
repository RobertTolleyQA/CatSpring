package com.qa.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.demo.persistence.domain.HouseDomain;
import com.qa.demo.persistence.dtos.HouseDTO;
import com.qa.demo.services.HouseServices;


@RestController
@RequestMapping("/house")
public class HouseController {
	
	private HouseServices service;

	@Autowired
	public HouseController(HouseServices service) {
		super();
		this.service = service;
	}

	@GetMapping("/readAll")
	public ResponseEntity<List<HouseDTO>> readAll() {
		return ResponseEntity.ok(this.service.readAll());
	}

	@GetMapping("/readOne/{id}")
	public ResponseEntity<HouseDTO> readOne(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.service.readOne(id));

	}

	@PostMapping("/create")
	public ResponseEntity<HouseDTO> create(@RequestBody HouseDomain house) {
		return new ResponseEntity<HouseDTO>(this.service.create(house), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<HouseDTO> update(@PathVariable("id") Long id, @RequestBody HouseDomain house) {
		return new ResponseEntity<HouseDTO>(this.service.update(id, house), HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		return this.service.delete(id) ? 
				new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


