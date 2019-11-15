package com.lab.software.engineering.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String ID_GEN_NAME = "Bill.idGenerator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN_NAME)
	@SequenceGenerator(allocationSize = 1, sequenceName = "seq_bill_id", name = ID_GEN_NAME)
	private Long id;

	private BigDecimal amount;

	private String description;

	@NotNull
	@ManyToOne(optional = false)
	private Trip trip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

}
