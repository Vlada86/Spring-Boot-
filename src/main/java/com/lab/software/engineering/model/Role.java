package com.lab.software.engineering.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String ID_GEN_NAME = "Role.idGenerator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN_NAME)
	@SequenceGenerator(allocationSize = 1, sequenceName = "seq_role_id", name = ID_GEN_NAME)
	private Long id;

	private String name;

	public Long getId() {
		return this.id;
	}

	public void setId(Long roleid) {
		this.id = roleid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
