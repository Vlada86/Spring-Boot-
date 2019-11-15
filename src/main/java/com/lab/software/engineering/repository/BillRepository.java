package com.lab.software.engineering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.software.engineering.model.Bill;

@Repository("bill")
public interface BillRepository extends JpaRepository<Bill, Long> {

	
	List<Bill> findByTripId(Long id);
}
