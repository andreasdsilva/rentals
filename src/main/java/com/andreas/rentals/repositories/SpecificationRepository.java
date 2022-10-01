package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreas.rentals.entities.Specification;

public interface SpecificationRepository extends JpaRepository<Specification, Long>{

}
