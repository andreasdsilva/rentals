package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreas.rentals.entities.Brand;
import com.andreas.rentals.entities.Car;
import com.andreas.rentals.entities.Category;
import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.exceptions.CredentialsException;
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
	private JTextField licensPlateTextField;
	private JLabel carRegisterWarningLabel;
	private JTextField descriptionTextField;
	private JTextField nameTextField;
	private JTextField specificationsListTextField;
	private JTextField dailyRateTextField;
	private JComboBox<Specification> specificationComboBox;
	private JComboBox<Brand> brandComboBox;
	private JComboBox<Category> categoriesComboBox;
	private JComboBox<String> colorsComboBox;
	private String specifications = "";

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
		newCarLabel.setBounds(309, 0, 175, 84);
		add(newCarLabel);

		JLabel brandNameLabel = new JLabel("Name:");
		brandNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		brandNameLabel.setBounds(43, 100, 99, 42);
		add(brandNameLabel);

		licensPlateTextField = new JTextField();
		licensPlateTextField.setBounds(134, 264, 271, 31);
		add(licensPlateTextField);
		licensPlateTextField.setColumns(10);

		carRegisterWarningLabel = new JLabel("");
		carRegisterWarningLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		carRegisterWarningLabel.setForeground(Color.RED);
		carRegisterWarningLabel.setBounds(248, 371, 240, 23);
		add(carRegisterWarningLabel);

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

		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(134, 106, 271, 31);
		add(nameTextField);

		categoriesComboBox = new JComboBox<>();
		categoriesComboBox.addItem(null);
		categoriesComboBox.setBounds(532, 160, 154, 31);
		add(categoriesComboBox);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCategory.setBounds(440, 154, 90, 42);
		add(lblCategory);

		JLabel lblSpecifications = new JLabel("Specifications:");
		lblSpecifications.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSpecifications.setBounds(513, 260, 99, 42);
		add(lblSpecifications);

		specificationComboBox = new JComboBox<>();
		specificationComboBox.addItem(null);
		specificationComboBox.setBounds(440, 299, 137, 31);
		add(specificationComboBox);

		JButton addSpecificationButton = new JButton("ADD");
		addSpecificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSpecification(specificationComboBox.getSelectedItem().toString());
			}
		});
		addSpecificationButton.setBounds(587, 298, 99, 33);
		add(addSpecificationButton);

		specificationsListTextField = new JTextField();
		specificationsListTextField.setBounds(440, 345, 248, 31);
		add(specificationsListTextField);
		specificationsListTextField.setEnabled(false);
		specificationsListTextField.setColumns(10);

		brandComboBox = new JComboBox<Brand>();
		brandComboBox.addItem(null);
		brandComboBox.setBounds(532, 213, 154, 31);
		add(brandComboBox);

		JLabel lblBrand = new JLabel("Brand:");
		lblBrand.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBrand.setBounds(440, 207, 82, 42);
		add(lblBrand);

		dailyRateTextField = new JTextField();
		dailyRateTextField.setColumns(10);
		dailyRateTextField.setBounds(134, 326, 99, 31);
		add(dailyRateTextField);

		JLabel lblDailyRate = new JLabel("Daily Rate ($):");
		lblDailyRate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDailyRate.setBounds(43, 323, 99, 37);
		add(lblDailyRate);

		JLabel colorLabel = new JLabel("Color:");
		colorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		colorLabel.setBounds(440, 74, 108, 95);
		add(colorLabel);

		colorsComboBox = new JComboBox<String>();
		colorsComboBox
				.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Black", "White", "Red", "Silver" }));
		colorsComboBox.setBounds(532, 106, 154, 31);
		add(colorsComboBox);

		registerCarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					checkFields();
					checkLicense();
					createNewCar();
					clearFields();
				} catch (Exception ex) {
					carRegisterWarningLabel.setText(ex.getMessage());
				}
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
		for (Specification specification : specificationService.findAll()) {
			specificationComboBox.addItem(specification);
		}

		for (Category category : categoryService.findAll()) {
			categoriesComboBox.addItem(category);
		}

		for (Brand brand : brandService.findAll()) {
			brandComboBox.addItem(brand);
		}
	}

	private void checkFields() throws CredentialsException {
		if (nameTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty()
				|| dailyRateTextField.getText().isEmpty() || licensPlateTextField.getText().isEmpty()
				|| specifications.isEmpty() || brandComboBox.getSelectedItem() == null
				|| categoriesComboBox.getSelectedItem() == null || colorsComboBox.getSelectedItem() == null) {
			throw new CredentialsException("All fields must be filled and not null!");
		}
	}

	public void checkLicense() {
		if (licensPlateTextField.getText().isEmpty())
			throw new CredentialsException("Please enter a license plate!");

		if (carService.findByLicense(licensPlateTextField.getText()) != null)
			throw new CredentialsException("License plate already exists!");
	}
	
	private void clearFields() {
		brandComboBox.setSelectedIndex(0);
		categoriesComboBox.setSelectedIndex(0);
		colorsComboBox.setSelectedIndex(0);
		specificationComboBox.setSelectedIndex(0);
		dailyRateTextField.setText("");
		specificationsListTextField.setText("");
		descriptionTextField.setText("");
		licensPlateTextField.setText("");
		nameTextField.setText("");
	}

	private void addSpecification(String specification) {
		if (!specifications.contains(specification)) {
			specifications += specifications.isEmpty() ? specification : ", " + specification;
			specificationsListTextField.setText(specification);
		}
	}

	private void goToHomePage() {
		main.getHomPane();
		main.setPanel(main.getHomPane().getRootPanel());
	}

	private void createNewCar() {
		Car car = new Car();
		car.setBrand((Brand) brandComboBox.getSelectedItem());
		car.setCategory((Category) categoriesComboBox.getSelectedItem());
		car.setColor((String) colorsComboBox.getSelectedItem());
		car.setDailyRate(Double.parseDouble(dailyRateTextField.getText()));
		car.setDescription(descriptionTextField.getText());
		car.setLicensePlate(licensPlateTextField.getText());
		car.setName(nameTextField.getText());
		carService.createCar(car);

		for (String specificationName : specifications.split(", ")) {
			Specification specification = specificationService.findByName(specificationName);
			if (specification != null)
				carSpecificationService.createCarSpecification(car, specification);
		}

		carRegisterWarningLabel.setText("Car " + car + " created!");
	}
}