package com.andreas.rentals.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.CarImage;
import com.andreas.rentals.repositories.CarImageRepository;

@Service
public class CarImageService {

		@Autowired
		private CarImageRepository carImageRepository;
		
		public List<CarImage> findAll() {
			return carImageRepository.findAll();
		}
		
		public CarImage findById( Long id ) {
			Optional<CarImage> obj = carImageRepository.findById(id);
			return obj.get();
		}
}
