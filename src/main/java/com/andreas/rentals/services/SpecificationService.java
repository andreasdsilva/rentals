package com.andreas.rentals.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.repositories.SpecificationRepository;

@Service
public class SpecificationService {

	@Autowired
	private SpecificationRepository specificationRepository;

	public List<Specification> findAll() {
		return specificationRepository.findAll();
	}

	public Specification findById(Long id) {
		Optional<Specification> obj = specificationRepository.findById(id);
		return obj.get();
	}

	public Specification findByName(String name) {
		return specificationRepository.findByName(name);
	}

	public void createSpecification(Specification specification) {
		specification.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		specificationRepository.save(specification);
	}
}
