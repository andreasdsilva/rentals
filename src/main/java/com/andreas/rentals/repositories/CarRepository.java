package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreas.rentals.entities.Car;

public interface CarRepository extends JpaRepository<Car, Long>{

}
