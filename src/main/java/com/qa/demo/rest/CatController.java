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

import com.qa.demo.persistence.domain.CatDomain;
import com.qa.demo.persistence.dtos.CatDTO;
import com.qa.demo.services.CatServices;

@RestController
@RequestMapping("/cats")
public class CatController {

	private CatServices service;

	@Autowired
	public CatController(CatServices service) {
		super();
		this.service = service;
	}

	@GetMapping("/readAll")
	public ResponseEntity<List<CatDTO>> readAll() {
		return ResponseEntity.ok(this.service.readAll());
	}

	@GetMapping("/readOne/{id}")
	public ResponseEntity<CatDTO> readOne(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.service.readOne(id));

	}

	@PostMapping("/create")
	public ResponseEntity<CatDTO> create(@RequestBody CatDomain cat) {
		return new ResponseEntity<CatDTO>(this.service.create(cat), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<CatDTO> update(@PathVariable("id") Long id, @RequestBody CatDomain cat) {
		return new ResponseEntity<CatDTO>(this.service.update(id, cat), HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		return this.service.delete(id) ? 
				new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
