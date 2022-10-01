package com.andreas.rentals.entities;

import java.sql.Date;

public class Brand {

	private Long id;
	private String name;
	private Date createdAt;
	
	public Brand(String name, Date createdAt) {
		super();
		this.name = name;
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}
}
