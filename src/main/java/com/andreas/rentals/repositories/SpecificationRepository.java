package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.andreas.rentals.entities.Specification;

public interface SpecificationRepository extends JpaRepository<Specification, Long> {
	@Query(value = "select * from specifications where name = ?1", nativeQuery = true)
	public Specification findByName(String name);
}
