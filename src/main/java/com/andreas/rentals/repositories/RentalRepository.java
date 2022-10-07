package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreas.rentals.entities.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long>{

}
