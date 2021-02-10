package com.qa.demo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.demo.persistence.domain.CatDomain;

@Repository
public interface CatRepo extends JpaRepository<CatDomain, Long>{
	
	

}
