package com.andreas.rentals.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Car;
import com.andreas.rentals.entities.CarSpecification;
import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.repositories.CarSpecificationRepository;

@Service
public class CarSpecificationService {

		@Autowired
		private CarSpecificationRepository carSpecificationRepository;
		
		public List<CarSpecification> findAll() {
			return carSpecificationRepository.findAll();
		}
		
		public CarSpecification findById( Long id ) {
			Optional<CarSpecification> obj = carSpecificationRepository.findById(id);
			return obj.get();
		}
		
		public void createCarSpecification(Car car, Specification specification) {			
			carSpecificationRepository.save(new CarSpecification(car, specification));
		}
}
