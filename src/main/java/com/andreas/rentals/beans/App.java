package com.andreas.rentals.beans;

import org.springframework.stereotype.Component;

import com.andreas.rentals.ui.MainFrame;

@Component
public class App {
	
	public void init() {
	    MainFrame main;
		try {
			main = new MainFrame();
			main.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	    
	}
}
