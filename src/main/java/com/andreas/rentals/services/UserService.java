package com.andreas.rentals.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.User;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.repositories.UserRepository;
import com.andreas.rentals.util.BeanUtil;

@Service
public class UserService {

		private static UserRepository userRepository = (UserRepository) BeanUtil.getBeanByName("userRepository");
		
		public List<User> findAll() {
			return userRepository.findAll();
		}
		
		public User findById( Long id ) {
			Optional<User> obj = userRepository.findById(id);
			return obj.get();
		}
		
		public User findByLogin( String login ) throws CredentialsException {
			List<User> users = findAll();
			
			for( User user : users ) 
			{
				if( user.getLogin().equals(login) )
					return user;
			}
			
			throw new CredentialsException( "User not found!" );
		}
		
		public void createUser( User user ) {
			user.setPassword( user.getPassword() );
			userRepository.save(user);
		}
		
		public void deleteUser( User user ) {
			userRepository.delete(user);
		}
		
		public User login( String login, String password ) throws CredentialsException {
			User user = findByLogin( login );
			System.out.println(user.getLogin() + "-" + user.getPassword() + " login method" );
			if( user.getLogin().equals(login) && user.getPassword().equals( password ))
			{
				return user;				
			}
			else
			{
				throw new CredentialsException("Invalid password");
			}
		}
		
	    private String hashPassword( String password ) {

	        MessageDigest messageDigest = null;
	        try {
	            messageDigest = MessageDigest.getInstance("SHA-256");
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        }
	        messageDigest.update(password.getBytes());
	        byte[] bytes = messageDigest.digest();

	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < bytes.length; i++) {
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }

	        return sb.toString();
	    }
}
