package com.lab.software.engineering.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lab.software.engineering.model.Employee;


public class EmployeePrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Employee employee;

	public EmployeePrincipal(Employee employee) {
		if (employee == null) {
			throw new IllegalArgumentException("Employee must not be null");
		}
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + employee.getRole().getName()));
	}

	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		return employee.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return employee != null;
	}
}
