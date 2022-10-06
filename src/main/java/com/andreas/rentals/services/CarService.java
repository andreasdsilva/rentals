package com.andreas.rentals.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Car;
import com.andreas.rentals.repositories.CarRepository;

@Service
public class CarService {

		@Autowired
		private CarRepository carRepository;
		
		public List<Car> findAll() {
			return carRepository.findAll();
		}
		
		public Car findById( Long id ) {
			Optional<Car> obj = carRepository.findById(id);
			return obj.get();
		}
		
		public void createCar(Car car) {
			car.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
			car.setAvailable(true);
			carRepository.save(car);
		}
		
		public Car findByLicense(String license ) {
			return carRepository.findByLicense(license);
		}
}
