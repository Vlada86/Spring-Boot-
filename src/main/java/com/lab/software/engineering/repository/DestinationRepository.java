package com.lab.software.engineering.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.software.engineering.model.Destination;

@Repository("destination")
public interface DestinationRepository extends JpaRepository<Destination, Long>{
	
	Optional<Destination> findById(Long id);
	
	Destination findByName(String name);

}
