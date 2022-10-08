package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.Brand;
import com.andreas.rentals.services.BrandService;
import com.andreas.rentals.util.BeanUtil;

@Component
public class BrandRegisterPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private BrandService brandService;
	private MainFrame main;
	private JTextField textField;
	private JLabel existingBrandLabel;

	public BrandRegisterPane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() {
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {
		brandService = (BrandService) BeanUtil.getBeanByName("brandService");
		this.main = main;
		setLayout(null);

		JLabel newBrandLabel = new JLabel("New Brand:");
		newBrandLabel.setFont(new Font("Arial", Font.BOLD, 15));
		newBrandLabel.setBounds(315, 0, 219, 129);
		add(newBrandLabel);

		JLabel brandNameLabel = new JLabel("Brand Name:");
		brandNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		brandNameLabel.setBounds(174, 163, 99, 42);
		add(brandNameLabel);

		textField = new JTextField();
		textField.setBounds(360, 169, 216, 31);
		add(textField);
		textField.setColumns(10);

		existingBrandLabel = new JLabel("");
		existingBrandLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		existingBrandLabel.setForeground(Color.RED);
		existingBrandLabel.setVisible(false);
		existingBrandLabel.setBounds(294, 237, 240, 23);
		add(existingBrandLabel);

		JButton btnNewButton = new JButton("Register Brand");
		btnNewButton.setBounds(262, 310, 206, 31);
		add(btnNewButton);

		JButton btnHomePage = new JButton("Home Page");
		btnHomePage.setBounds(548, 11, 137, 31);
		add(btnHomePage);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewBrand(textField.getText().toString().trim());
			}
		});

		btnHomePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToHomePage();
			}
		});
	}

	private void goToHomePage() {
		existingBrandLabel.setText("");
		textField.setText("");
		main.getHomPane();
		main.setPanel(main.getHomPane().getRootPanel());
	}

	private void createNewBrand(String name) {
		if (!name.isEmpty()) {
			Brand existingBrand = brandService.findByName(name);

			if (existingBrand == null) {
				Brand brand = new Brand();
				brand.setName(name);
				brandService.createBrand(brand);
				existingBrandLabel.setText("Brand " + brand.getName() + " created!");
			} else {
				existingBrandLabel.setText("Brand already exists!");
			}

			existingBrandLabel.setVisible(true);
			main.setPanel(getRootPanel());
		} else {
			existingBrandLabel.setText("Please enter a Brand name!");
		}
	}
}
