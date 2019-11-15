package com.lab.software.engineering.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Destination implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String ID_GEN_NAME = "Destination.idGenerator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN_NAME)
	@SequenceGenerator(allocationSize = 1, sequenceName = "seq_destination_id", name = ID_GEN_NAME)
	private Long id;

	private BigDecimal dailyAllowance;
	private String name;

	
	@ManyToOne(optional = false)
	private Country country;

	public Long getId() {
		return this.id;
	}

	public void setId(Long destinationid) {
		this.id = destinationid;
	}

	public BigDecimal getDailyAllowance() {
		return dailyAllowance;
	}

	public void setDailyAllowance(BigDecimal dailyAllowance) {
		this.dailyAllowance = dailyAllowance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
