package com.andreas.rentals.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.User;
import com.andreas.rentals.services.UserService;
import com.andreas.rentals.ui.MainFrame;

@Component
public class App {
	
	public void init() {
	    MainFrame main = new MainFrame();
	    
	    main.setVisible(true);
	}
}
