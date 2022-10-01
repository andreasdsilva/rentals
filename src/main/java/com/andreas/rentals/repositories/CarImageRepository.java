package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreas.rentals.entities.CarImage;

public interface CarImageRepository extends JpaRepository<CarImage, Long>{

}
