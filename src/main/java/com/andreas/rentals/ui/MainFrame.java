package com.andreas.rentals.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

@Component
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private final LoginPane loginPane;
	private final RegisterPane registerPane;
	private final HomePane homePane;
	private final BrandRegisterPane brandRegisterPane;
	private final SpecificationRegisterPane specificationRegisterPane;
	private final CategoryRegisterPane categoryRegisterPane;
	private final CustomerRegisterPane customerRegisterPane;
 
	public MainFrame() {

		loginPane = new LoginPane(this);
		registerPane = new RegisterPane(this);
		homePane = new HomePane(this);
		brandRegisterPane = new BrandRegisterPane(this);
		specificationRegisterPane = new SpecificationRegisterPane(this);
		categoryRegisterPane = new CategoryRegisterPane(this);
		customerRegisterPane = new CustomerRegisterPane(this);

		this.setTitle("Rentals");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(loginPane.getRootPanel());
		setSize(720, 480);

		setLocationRelativeTo(null);
	}

	public void resetPanel() {
		setPanel(loginPane.getRootPanel());
	}

	public void setPanel(JPanel panel) {
		getContentPane().removeAll();
		getContentPane().add(panel);
		revalidate();
		repaint();
	}

	public LoginPane getLoginPane() {
		return loginPane;
	}

	public RegisterPane getRegisterPane() {
		return registerPane;
	}

	public HomePane getHomPane() {
		return homePane;
	}

	public BrandRegisterPane getBrandRegisterPane() {
		return brandRegisterPane;
	}

	public SpecificationRegisterPane getSpecificationRegisterPane() {
		return specificationRegisterPane;
	}

	public CategoryRegisterPane getCategoryRegisterPane() {
		return categoryRegisterPane;
	}
	
	public CustomerRegisterPane getCustomerRegisterPane() {
		return customerRegisterPane;
	}
	
	public void init() {
		this.setVisible(true);
	}

}