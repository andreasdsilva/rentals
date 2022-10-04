package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.services.SpecificationService;
import com.andreas.rentals.util.BeanUtil;

public class SpecificationRegisterPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private SpecificationService specificationService;
	private MainFrame main;
	private JTextField nameTextField;
	private JLabel specificationWarningLabel;
	private JTextField descriptionTextField;

	public SpecificationRegisterPane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() {
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {
		specificationService = (SpecificationService) BeanUtil.getBeanByName("specificationService");
		this.main = main;
		setLayout(null);

		JLabel newBrandLabel = new JLabel("New Specification:");
		newBrandLabel.setFont(new Font("Arial", Font.BOLD, 15));
		newBrandLabel.setBounds(309, 0, 219, 129);
		add(newBrandLabel);

		JLabel brandNameLabel = new JLabel("Name:");
		brandNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		brandNameLabel.setBounds(169, 163, 99, 42);
		add(brandNameLabel);

		nameTextField = new JTextField();
		nameTextField.setBounds(360, 169, 271, 31);
		add(nameTextField);
		nameTextField.setColumns(10);

		specificationWarningLabel = new JLabel("");
		specificationWarningLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		specificationWarningLabel.setForeground(Color.RED);
		specificationWarningLabel.setVisible(false);
		specificationWarningLabel.setBounds(280, 342, 240, 23);
		add(specificationWarningLabel);

		JButton btnNewButton = new JButton("Register Specification");
		btnNewButton.setBounds(264, 405, 206, 31);
		add(btnNewButton);

		JButton btnHomePage = new JButton("Home Page");
		btnHomePage.setBounds(573, 0, 137, 31);
		add(btnHomePage);

		JLabel specificationDescriptionLabel = new JLabel("Description:");
		specificationDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		specificationDescriptionLabel.setBounds(169, 240, 99, 42);
		add(specificationDescriptionLabel);

		descriptionTextField = new JTextField();
		descriptionTextField.setColumns(10);
		descriptionTextField.setBounds(360, 246, 271, 73);
		add(descriptionTextField);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewSpecification(nameTextField.getText().toString().trim(),
						descriptionTextField.getText().toString());
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
		specificationWarningLabel.setText("");
		nameTextField.setText("");
		descriptionTextField.setText("");
		main.getHomPane();
		main.setPanel(main.getHomPane().getRootPanel());
	}

	private void createNewSpecification(String name, String description) {
		if (!name.isEmpty()) {
			Specification existingSpecification = specificationService.findByName(name);

			if (existingSpecification == null) {
				Specification specification = new Specification();
				specification.setName(name);
				specification.setDescription(description);
				specificationService.createSpecification(specification);
				specificationWarningLabel.setText("Specification " + specification.getName() + " created!");
			} else {
				specificationWarningLabel.setText("Specification already exists!");
			}

			specificationWarningLabel.setVisible(true);
			main.setPanel(getRootPanel());
		} else {
			specificationWarningLabel.setText("Please enter a Specification name!");
		}
	}
}
