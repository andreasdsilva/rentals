package com.andreas.rentals.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.andreas.rentals.entities.Car;
import com.andreas.rentals.entities.CarSpecification;

public interface CarSpecificationRepository extends JpaRepository<CarSpecification, Long>{
	@Query(value = "select * from car_specifications where car_id = ?1", nativeQuery = true)
	public List<CarSpecification> findByCar(Car car);
}
