package com.andreas.rentals.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.andreas.rentals.entities.Category;
import com.andreas.rentals.entities.User;
import com.andreas.rentals.services.CategoryService;
import com.andreas.rentals.services.UserService;

@Configuration
@Profile("test")
public class Config implements CommandLineRunner {

	@Autowired
	private UserService userService;// = (UserService) BeanUtil.getBeanByName("userService");
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public void run(String... args) throws Exception {
		User u = new User("admin", "admin");
		userService.createUser(u);
		
		Category cat = new Category();
		cat.setDescription("aaa");
		cat.setName("categ");
		categoryService.createSpecification(cat);
	}

}
