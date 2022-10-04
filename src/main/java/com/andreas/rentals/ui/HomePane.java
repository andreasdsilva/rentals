package com.andreas.rentals.ui;

import javax.swing.JPanel;

import org.springframework.stereotype.Component;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class HomePane extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MainFrame main;
 	
	public HomePane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() { 
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {
		this.main = main;
		setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		welcomeLabel.setBounds(22, 11, 163, 62);
		add(welcomeLabel);
		
		JLabel rentalsLabel = new JLabel("Rentals:");
		rentalsLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		rentalsLabel.setBounds(132, 71, 83, 43);
		add(rentalsLabel);
		
		JLabel newRegisterLabel = new JLabel("Register new:");
		newRegisterLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		newRegisterLabel.setBounds(411, 71, 97, 43);
		add(newRegisterLabel);
		
		JButton newRentalbtn = new JButton("Rent a Car");
		newRentalbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		newRentalbtn.setBounds(132, 141, 163, 50);
		add(newRentalbtn);
		
		JButton btnConsultRental = new JButton("Consult Rental");
		btnConsultRental.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConsultRental.setBounds(132, 244, 163, 50);
		add(btnConsultRental);
		
		JButton btnBrand = new JButton("Brand");
		btnBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToBrandRegister();
			}
		});
		
		btnBrand.setBounds(411, 122, 163, 50);
		add(btnBrand);
		
		JButton btnSpecification = new JButton("Specification");
		btnSpecification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnSpecification.setBounds(411, 183, 163, 50);
		add(btnSpecification);
		
		JButton btnCustomer = new JButton("Customer");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnCustomer.setBounds(411, 305, 163, 50);
		add(btnCustomer);
		
		JButton btnCategory = new JButton("Category");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnCategory.setBounds(411, 244, 163, 50);
		add(btnCategory);
		
		JButton btnCar = new JButton("Car");
		btnCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnCar.setBounds(411, 366, 163, 50);
		add(btnCar);
	}
	
	private void goToBrandRegister() {
		main.getBrandRegisterPane();
		main.setPanel( main.getBrandRegisterPane().getRootPanel() );
	}
}
