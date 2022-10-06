package com.andreas.rentals.services;

import java.sql.Date;
import java.util.Calendar;
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

	public Customer findById(Long id) {
		Optional<Customer> obj = customerRepository.findById(id);
		return obj.get();
	}

	public Customer findByLicense(long license) {
		for(Customer brand : findAll()) {
			if( brand.getDriverLicense() == license) {
				return brand;
			}
		}
		return null;
	}

	public void createCustomer(Customer customer) {
		customer.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		customer.setUpdatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		customerRepository.save(customer);
	}
}
