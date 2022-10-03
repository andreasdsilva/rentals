package com.andreas.rentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.andreas.rentals.beans.App;

@SpringBootApplication
public class RentalsApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		ApplicationContext context = SpringApplication.run(RentalsApplication.class, args);
		
		App app = context.getBean(App.class);
		
		app.init();
	}

}
