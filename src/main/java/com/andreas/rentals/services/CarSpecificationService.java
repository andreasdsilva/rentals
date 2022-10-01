package com.andreas.rentals.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.CarSpecification;
import com.andreas.rentals.repositories.CarSpecificationRepository;

@Service
public class CarSpecificationService {

		@Autowired
		private CarSpecificationRepository CarSpecificationRepository;
		
		public List<CarSpecification> findAll() {
			return CarSpecificationRepository.findAll();
		}
		
		public CarSpecification findById( Long id ) {
			Optional<CarSpecification> obj = CarSpecificationRepository.findById(id);
			return obj.get();
		}
}
