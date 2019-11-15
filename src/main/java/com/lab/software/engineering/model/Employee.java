package com.lab.software.engineering.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String ID_GEN_NAME = "Employee.idGenerator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN_NAME)
	@SequenceGenerator(allocationSize = 1, sequenceName = "seq_employee_id", name = ID_GEN_NAME)
	private Long id;

	@Email
	private String email;

	@Size(min = 2, max = 30)
	private String firstName;

	private String password;

	private String lastName;

	private String username;

	@NotNull
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	private Role role;

	public Long getId() {
		return this.id;
	}

	public void setId(Long employeeid) {
		this.id = employeeid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}