package com.andreas.rentals.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.repositories.SpecificationRepository;

@Service
public class SpecificationService {

		@Autowired
		private SpecificationRepository SpecificationRepository;
		
		public List<Specification> findAll() {
			return SpecificationRepository.findAll();
		}
		
		public Specification findById( Long id ) {
			Optional<Specification> obj = SpecificationRepository.findById(id);
			return obj.get();
		}
}
