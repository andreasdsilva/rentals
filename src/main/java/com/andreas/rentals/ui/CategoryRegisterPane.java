package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreas.rentals.entities.Category;
import com.andreas.rentals.services.CategoryService;
import com.andreas.rentals.util.BeanUtil;

public class CategoryRegisterPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private CategoryService categoryService;
	private MainFrame main;
	private JTextField nameTextField;
	private JLabel categoryWarningLabel;
	private JTextField descriptionTextField;

	public CategoryRegisterPane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() {
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {
		categoryService = (CategoryService) BeanUtil.getBeanByName("categoryService");
		this.main = main;
		setLayout(null);

		JLabel newBrandLabel = new JLabel("New Category:");
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

		categoryWarningLabel = new JLabel("");
		categoryWarningLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		categoryWarningLabel.setForeground(Color.RED);
		categoryWarningLabel.setVisible(false);
		categoryWarningLabel.setBounds(280, 342, 240, 23);
		add(categoryWarningLabel);

		JButton btnNewButton = new JButton("Register Category");
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
				createNewCategory(nameTextField.getText().toString().trim(),
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
		categoryWarningLabel.setText("");
		nameTextField.setText("");
		descriptionTextField.setText("");
		main.getHomPane();
		main.setPanel(main.getHomPane().getRootPanel());
	}

	private void createNewCategory(String name, String description) {
		if (!name.isEmpty()) {
			Category existingCategory = categoryService.findByName(name);

			if (existingCategory == null) {
				Category category = new Category();
				category.setName(name);
				category.setDescription(description);
				categoryService.createSpecification(category);
				categoryWarningLabel.setText("Category " + category.getName() + " created!");
			} else {
				categoryWarningLabel.setText("Category already exists!");
			}

			categoryWarningLabel.setVisible(true);
			main.setPanel(getRootPanel());
		} else {
			categoryWarningLabel.setText("Please enter a Category name!");
		}
	}
}
