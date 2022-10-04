package com.andreas.rentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.andreas.rentals.ui.MainFrame;

@SpringBootApplication
public class RentalsApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		ApplicationContext context = SpringApplication.run(RentalsApplication.class, args);		
		
		MainFrame app = context.getBean(MainFrame.class);		
		app.init();
	}

}
