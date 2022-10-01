package com.andreas.rentals.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cars_specifications")
public class CarSpecification implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	@OneToMany
	@JoinColumn(name = "specification_id")
	private Set<Specification> specifications = new HashSet<>();

	public CarSpecification(Car car, Set<Specification> specifications) {
		super();
		this.car = car;
		this.specifications = specifications;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Set<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}

	public Long getId() {
		return id;
	}
	
	
}
