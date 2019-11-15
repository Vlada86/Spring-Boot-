package com.lab.software.engineering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.software.engineering.model.Trip;

@Repository("trip")
public interface TripRepository extends JpaRepository<Trip, Long> {

	List<Trip> findByEmployeeId(Long id);
	
	

}
