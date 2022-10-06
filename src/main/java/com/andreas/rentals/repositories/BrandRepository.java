package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.andreas.rentals.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long>{

}
