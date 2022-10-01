package com.andreas.rentals.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.User;
import com.andreas.rentals.repositories.UserRepository;

@Service
public class UserService {

		@Autowired
		private UserRepository userRepository;
		
		public List<User> findAll() {
			return userRepository.findAll();
		}
		
		public User findById( Long id ) {
			Optional<User> obj = userRepository.findById(id);
			return obj.get();
		}
		
		public void createUser( User user ) {
			userRepository.save(user);
		}
		
		public void deleteUser( User user ) {
			userRepository.delete(user);
		}
}
