package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreas.rentals.entities.CarSpecification;

public interface CarSpecificationRepository extends JpaRepository<CarSpecification, Long>{

}
