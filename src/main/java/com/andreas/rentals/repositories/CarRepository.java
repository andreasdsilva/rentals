package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.andreas.rentals.entities.Car;

public interface CarRepository extends JpaRepository<Car, Long>{

	@Query(value = "select * from cars where license_plate = ?1", nativeQuery = true)
	public Car findByLicense(String license);
}
