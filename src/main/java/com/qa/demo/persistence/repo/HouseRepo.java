package com.qa.demo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.demo.persistence.domain.HouseDomain;

public interface HouseRepo extends JpaRepository<HouseDomain, Long>{

}
