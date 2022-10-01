package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreas.rentals.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
