package com.lab.software.engineering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.software.engineering.model.Employee;

@Repository("employee")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findById(Long id);
	
	List<Employee> findByFirstName(String firstName);

	Employee findByLastName(String lastName);

	Employee findByUsername(String username);
}
