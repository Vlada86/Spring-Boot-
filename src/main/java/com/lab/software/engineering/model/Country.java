package com.lab.software.engineering.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String ID_GEN_NAME = "Country.idGenerator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN_NAME)
	@SequenceGenerator(allocationSize = 1, sequenceName = "seq_country_id", name = ID_GEN_NAME)
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long countryid) {
		this.id = countryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
