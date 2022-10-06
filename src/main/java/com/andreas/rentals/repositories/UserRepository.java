package com.andreas.rentals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.andreas.rentals.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "SELECT * FROM USERS WHERE LOGIN = ?1", nativeQuery = true)
	public User findByLogin(String login);
}
