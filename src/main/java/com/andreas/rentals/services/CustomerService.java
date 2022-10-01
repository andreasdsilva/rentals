package com.andreas.rentals.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Customer;
import com.andreas.rentals.repositories.CustomerRepository;

@Service
public class CustomerService {

		@Autowired
		private CustomerRepository customerRepository;
		
		public List<Customer> findAll() {
			return customerRepository.findAll();
		}
		
		public Customer findById( Long id ) {
			Optional<Customer> obj = customerRepository.findById(id);
			return obj.get();
		}
}
