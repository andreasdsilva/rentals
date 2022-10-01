package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreas.rentals.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
