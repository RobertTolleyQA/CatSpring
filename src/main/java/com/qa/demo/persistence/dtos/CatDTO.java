package com.qa.demo.persistence.dtos;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CatDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int age;
	
	private String name;
	
	private int meowVolume;

	public CatDTO(Long id, int age, String name, int meowVolume) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.meowVolume = meowVolume;
	}

	public CatDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
//	Removed age getters and setters to make age private

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMeowVolume() {
		return meowVolume;
	}

	public void setMeowVolume(int meowVolume) {
		this.meowVolume = meowVolume;
	}
	
	
	
	




}
