package com.andreas.rentals.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.andreas.rentals.entities.Rental;
import com.andreas.rentals.repositories.RentalRepository;

public class RentalService {

	@Autowired
	private RentalRepository rentalRepository;

	public List<Rental> findAll() {
		return rentalRepository.findAll();
	}

	public Rental findById(Long id) {
		Optional<Rental> obj = rentalRepository.findById(id);
		return obj.get();
	}

	public void createRental(Rental rental) {
		rental.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		rental.setUpdatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		rentalRepository.save(rental);
	}
}
