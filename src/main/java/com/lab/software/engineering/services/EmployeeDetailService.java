package com.lab.software.engineering.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lab.software.engineering.model.Employee;
import com.lab.software.engineering.repository.EmployeeRepository;

@Service
public class EmployeeDetailService implements UserDetailsService {
	
	@Autowired
	EmployeeRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = repo.findByUsername(username);
		if (employee == null) {
			throw new UsernameNotFoundException(username);
		}
		return new EmployeePrincipal(employee);
	}

}
