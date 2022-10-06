package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreas.rentals.entities.Brand;
import com.andreas.rentals.entities.Car;
import com.andreas.rentals.entities.Category;
import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.services.BrandService;
import com.andreas.rentals.services.CarService;
import com.andreas.rentals.services.CarSpecificationService;
import com.andreas.rentals.services.CategoryService;
import com.andreas.rentals.services.SpecificationService;
import com.andreas.rentals.util.BeanUtil;

public class CarRegisterPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private CarService carService;
	private CategoryService categoryService;
	private SpecificationService specificationService;
	private BrandService brandService;
	private CarSpecificationService carSpecificationService;
	private MainFrame main;
	private JTextField nameTextField;
	private JLabel specificationWarningLabel;
	private JTextField descriptionTextField;
	private JTextField textField;
	private JTextField specificationsListTextField;
	private JTextField textField_2;
	private JComboBox<Specification> specificationComboBox;
	private JComboBox<Brand> brandComboBox;
	private JComboBox<Category> categoriesComboBox;
	
	public CarRegisterPane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() {
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {
		carService = (CarService) BeanUtil.getBeanByName("carService");
		categoryService = (CategoryService) BeanUtil.getBeanByName("categoryService");
		specificationService = (SpecificationService) BeanUtil.getBeanByName("specificationService");
		brandService = (BrandService) BeanUtil.getBeanByName("brandService");
		carSpecificationService = (CarSpecificationService) BeanUtil.getBeanByName("carSpecificationService");
		
		this.main = main;
		setLayout(null);

		JLabel newCarLabel = new JLabel("New Car:");
		newCarLabel.setFont(new Font("Arial", Font.BOLD, 15));
		newCarLabel.setBounds(309, 0, 219, 129);
		add(newCarLabel);

		JLabel brandNameLabel = new JLabel("Name:");
		brandNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		brandNameLabel.setBounds(43, 100, 99, 42);
		add(brandNameLabel);

		nameTextField = new JTextField();
		nameTextField.setBounds(134, 264, 271, 31);
		add(nameTextField);
		nameTextField.setColumns(10);

		specificationWarningLabel = new JLabel("");
		specificationWarningLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		specificationWarningLabel.setForeground(Color.RED);
		specificationWarningLabel.setVisible(false);
		specificationWarningLabel.setBounds(280, 342, 240, 23);
		add(specificationWarningLabel);

		JButton registerCarBtn = new JButton("Register Car");
		registerCarBtn.setBounds(264, 405, 206, 31);
		add(registerCarBtn);

		JButton btnHomePage = new JButton("Home Page");
		btnHomePage.setBounds(573, 0, 137, 31);
		add(btnHomePage);

		JLabel specificationDescriptionLabel = new JLabel("Description:");
		specificationDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		specificationDescriptionLabel.setBounds(43, 166, 99, 42);
		add(specificationDescriptionLabel);

		descriptionTextField = new JTextField();
		descriptionTextField.setColumns(10);
		descriptionTextField.setBounds(134, 166, 271, 73);
		add(descriptionTextField);
		
		JLabel lblLicensePlate = new JLabel("License Plate:");
		lblLicensePlate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLicensePlate.setBounds(43, 258, 99, 37);
		add(lblLicensePlate);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(134, 106, 271, 31);
		add(textField);
		
		categoriesComboBox = new JComboBox<>();
		categoriesComboBox.setBounds(531, 106, 154, 31);
		add(categoriesComboBox);
		
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCategory.setBounds(439, 100, 90, 42);
		add(lblCategory);
		
		JLabel lblSpecifications = new JLabel("Specifications:");
		lblSpecifications.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSpecifications.setBounds(512, 219, 99, 42);
		add(lblSpecifications);
		
		specificationComboBox = new JComboBox<>();		
		specificationComboBox.setBounds(439, 258, 137, 31);
		add(specificationComboBox);
		
		JButton addSpecificationButton = new JButton("ADD");
		addSpecificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addSpecificationButton.setBounds(586, 257, 99, 33);
		add(addSpecificationButton);
		
		specificationsListTextField = new JTextField();
		specificationsListTextField.setBounds(439, 304, 248, 31);
		add(specificationsListTextField);
		specificationsListTextField.setEnabled(false);
		specificationsListTextField.setColumns(10);
		
		brandComboBox = new JComboBox<Brand>();
		brandComboBox.setBounds(531, 172, 154, 31);
		add(brandComboBox);
		
		JLabel lblBrand = new JLabel("Brand:");
		lblBrand.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBrand.setBounds(439, 166, 82, 42);
		add(lblBrand);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(134, 326, 99, 31);
		add(textField_2);
		
		JLabel lblDailyRate = new JLabel("Daily Rate:");
		lblDailyRate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDailyRate.setBounds(43, 323, 99, 37);
		add(lblDailyRate);

		registerCarBtn.addActionListener(new ActionListener() {
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
	
	public void populateComboBox() {
		for( Specification specification : specificationService.findAll()) {
			specificationComboBox.addItem(specification);
		}
		
		for( Category category : categoryService.findAll() ) {
			categoriesComboBox.addItem(category);
		}
		
		for( Brand brand : brandService.findAll() ) {
			brandComboBox.addItem(brand);
		}
	}

	private void goToHomePage() {
		main.getHomPane();
		main.setPanel(main.getHomPane().getRootPanel());
	}

	private void createNewSpecification(String name, String description) {
		Car car = new Car();
		//carService.createCar( car );
	}
}