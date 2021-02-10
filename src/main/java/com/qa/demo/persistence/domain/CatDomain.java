package com.qa.demo.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CatDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int age;
	
	private String name;
	
	private int meowVolume;
	
	@ManyToOne
	private HouseDomain myHouse;

	public CatDomain(Long id, int age, String name, int meowVolume, HouseDomain myHouse) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.meowVolume = meowVolume;
		this.myHouse = myHouse;
	}
	
	

	public CatDomain(int age, String name, int meowVolume, HouseDomain myHouse) {
		super();
		this.age = age;
		this.name = name;
		this.meowVolume = meowVolume;
		this.myHouse = myHouse;
	}



	public CatDomain() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

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

	public HouseDomain getMyHouse() {
		return myHouse;
	}

	public void setMyHouse(HouseDomain myHouse) {
		this.myHouse = myHouse;
	}


}
