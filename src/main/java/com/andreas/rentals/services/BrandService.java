package com.andreas.rentals.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Brand;
import com.andreas.rentals.repositories.BrandRepository;

@Service
public class BrandService {

		@Autowired
		private BrandRepository brandRepository;
		
		public List<Brand> findAll() {
			return brandRepository.findAll();
		}
		
		public Brand findById( Long id ) {
			Optional<Brand> obj = brandRepository.findById(id);
			return obj.get();
		}
}
