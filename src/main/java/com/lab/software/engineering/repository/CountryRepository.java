package com.lab.software.engineering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.software.engineering.model.Country;

@Repository("country")
public interface CountryRepository extends JpaRepository<Country, Long> {

	Country findByName(String name);

}
