package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.Car;
import com.andreas.rentals.entities.CarSpecification;
import com.andreas.rentals.entities.Category;
import com.andreas.rentals.entities.Customer;
import com.andreas.rentals.entities.Rental;
import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.services.CarService;
import com.andreas.rentals.services.CarSpecificationService;
import com.andreas.rentals.services.CategoryService;
import com.andreas.rentals.services.CustomerService;
import com.andreas.rentals.services.RentalService;
import com.andreas.rentals.services.SpecificationService;
import com.andreas.rentals.util.BeanUtil;

@Component
public class RentalPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private RentalService rentalService;
	private CarSpecificationService carSpecificationService;
	private CarService carService;
	private SpecificationService specificationService;
	private CategoryService categoryService;
	private CustomerService customerService;
	private Customer customer;
	private MainFrame main;
	private JTextField driverLicenseTextField;
	private JTextField customersTextField;
	private JFormattedTextField startDateTextField;
	private JFormattedTextField endDateTextField;
	private JTextField totalTextField;
	private JLabel lblWarning;
	private JComboBox<Category> categoryComboBox;
	private JComboBox<Specification> specificationComboBox;
	private JCheckBox chckbxCategory;
	private JCheckBox chckbxSpecifications;
	private DefaultTableModel tableModel;

	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private JTable tableAvailableCars;
	private JTextField carIdTextField;

	public RentalPane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() {
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {

		rentalService = (RentalService) BeanUtil.getBeanByName("rentalService");
		carSpecificationService = (CarSpecificationService) BeanUtil.getBeanByName("carSpecificationService");
		specificationService = (SpecificationService) BeanUtil.getBeanByName("specificationService");
		categoryService = (CategoryService) BeanUtil.getBeanByName("categoryService");
		customerService = (CustomerService) BeanUtil.getBeanByName("customerService");
		carService = (CarService) BeanUtil.getBeanByName("carService");

		this.main = main;
		setLayout(null);

		JButton btnHomePage = new JButton("Home Page");
		btnHomePage.setBounds(548, 11, 137, 31);
		add(btnHomePage);

		JLabel newRentalLabel = new JLabel("New Rental:");
		newRentalLabel.setFont(new Font("Arial", Font.BOLD, 15));
		newRentalLabel.setBounds(309, 0, 175, 84);
		add(newRentalLabel);

		JLabel lblDriverLicense = new JLabel("Driver License:");
		lblDriverLicense.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDriverLicense.setBounds(32, 336, 90, 42);
		add(lblDriverLicense);

		driverLicenseTextField = new JTextField();
		driverLicenseTextField.setBounds(132, 342, 141, 31);
		add(driverLicenseTextField);
		driverLicenseTextField.setColumns(10);

		DateFormatter df = new DateFormatter(format);

		JButton btnSearchCustomer = new JButton("Search");
		btnSearchCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long license = Long.parseLong(driverLicenseTextField.getText());
					searchByLicense(license);
					lblWarning.setText("");
				} catch (Exception ex) {
					lblWarning.setText("Please enter a valid license, only numbers!");
				}

			}
		});
		btnSearchCustomer.setBounds(283, 342, 89, 31);
		add(btnSearchCustomer);

		customersTextField = new JTextField();
		customersTextField.setEnabled(false);
		customersTextField.setBounds(32, 378, 340, 31);
		add(customersTextField);
		customersTextField.setColumns(10);

		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblStartDate.setBounds(32, 76, 90, 42);
		add(lblStartDate);

		startDateTextField = new JFormattedTextField(df);
		startDateTextField.setBounds(99, 84, 71, 26);
		startDateTextField.setValue(new Date(Calendar.getInstance().getTime().getTime()));
		add(startDateTextField);
		startDateTextField.setColumns(10);

		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndDate.setBounds(180, 76, 90, 42);
		add(lblEndDate);

		endDateTextField = new JFormattedTextField(df);
		endDateTextField.setColumns(10);
		endDateTextField.setBounds(240, 84, 78, 26);
		endDateTextField.setValue(new Date(Calendar.getInstance().getTime().getTime()));
		add(endDateTextField);

		JLabel lblNotAvailable = new JLabel("Car not available in the specified dates!");
		lblNotAvailable.setVisible(false);
		lblNotAvailable.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNotAvailable.setForeground(Color.RED);
		lblNotAvailable.setBounds(231, 411, 288, 26);
		add(lblNotAvailable);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTotal.setBounds(382, 372, 90, 42);
		add(lblTotal);

		totalTextField = new JTextField();
		totalTextField.setText("$");
		totalTextField.setEnabled(false);
		totalTextField.setColumns(10);
		totalTextField.setBounds(423, 380, 125, 26);
		add(totalTextField);

		lblWarning = new JLabel("");
		lblWarning.setForeground(Color.RED);
		lblWarning.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWarning.setBounds(165, 411, 387, 26);
		add(lblWarning);

		JButton btnRentNow = new JButton("Rent Now");
		btnRentNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					checkDateFields();
					createNewRental();
					lblWarning.setText("Rental done!");
				} catch (ParseException ex) {
					lblWarning.setText("Please Enter a valid date!");
					ex.printStackTrace();
				} catch (Exception ex) {
					lblWarning.setText(ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		btnRentNow.setBounds(558, 378, 127, 31);
		add(btnRentNow);

		JLabel dateFormatTextField = new JLabel("(dd/MM/yyyy)");
		dateFormatTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		dateFormatTextField.setBounds(332, 76, 90, 42);
		add(dateFormatTextField);

		categoryComboBox = new JComboBox<Category>();
		categoryComboBox.setEnabled(false);
		categoryComboBox.setBounds(423, 84, 104, 26);
		add(categoryComboBox);

		specificationComboBox = new JComboBox<Specification>();
		specificationComboBox.setEnabled(false);
		specificationComboBox.setBounds(542, 84, 137, 26);
		add(specificationComboBox);

		chckbxCategory = new JCheckBox("Category:");
		chckbxCategory.setBounds(422, 61, 97, 23);
		add(chckbxCategory);

		chckbxCategory.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					categoryComboBox.setEnabled(true);
				} else {
					categoryComboBox.setEnabled(true);
				}
			}
		});

		chckbxSpecifications = new JCheckBox("Specifications:");
		chckbxSpecifications.setBounds(542, 61, 97, 23);
		add(chckbxSpecifications);

		chckbxSpecifications.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					specificationComboBox.setEnabled(true);
				} else {
					specificationComboBox.setEnabled(true);
				}
			}
		});

		JButton btnFilter = new JButton("Filter");
		btnFilter.setBounds(588, 341, 97, 32);
		add(btnFilter);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 116, 653, 215);
		add(scrollPane);

		tableAvailableCars = new JTable();
		tableAvailableCars.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableAvailableCars.setEnabled(false);
		scrollPane.setViewportView(tableAvailableCars);
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Car Id");
		tableModel.addColumn("Daily Rate");
		tableModel.addColumn("Brand");
		tableModel.addColumn("Name");
		tableModel.addColumn("Plate");
		tableModel.addColumn("Category");
		tableModel.addColumn("Color");
		tableModel.addColumn("Specifications");
		tableAvailableCars.setModel(tableModel);

		JLabel lblCarId = new JLabel("Car ID:");
		lblCarId.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCarId.setBounds(382, 336, 90, 42);
		add(lblCarId);

		carIdTextField = new JTextField();
		carIdTextField.setColumns(10);
		carIdTextField.setBounds(423, 342, 58, 31);
		add(carIdTextField);

		JButton btnSearchCar = new JButton("Search");
		btnSearchCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchCar();
			}
		});
		btnSearchCar.setBounds(492, 342, 86, 31);
		add(btnSearchCar);

		btnFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					checkDateFields();
					checkBoxes();
					searchCarsList();
					lblWarning.setText("");
				} catch (ParseException ex) {
					lblWarning.setText("Please Enter a valid date!");
					ex.printStackTrace();
				} catch (Exception ex) {
					lblWarning.setText(ex.getMessage());

					ex.printStackTrace();
				}
			}
		});

		btnHomePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToHomePage();
				clearFields();
			}
		});
	}

	private void searchCar() {
		try {
			Car car = carService.findById(Long.parseLong(carIdTextField.getText()));

			if (car == null) {
				lblWarning.setText("ID not found!");
			} else {
				lblWarning.setText("");
				Date formattedStartDate = new Date(format.parse(startDateTextField.getText()).getTime());
				Date formattedEndDate = new Date(format.parse(endDateTextField.getText()).getTime());
				long diff = formattedEndDate.getTime() - formattedStartDate.getTime();
				float days = (diff / (1000 * 60 * 60 * 24));
				totalTextField.setText("$ " + days * car.getDailyRate());
			}
		} catch (Exception ex) {
			totalTextField.setText("$ ");
			lblWarning.setText("Pleas enter a valid ID!");
		}
	}

	private void clearFields() {
		carIdTextField.setText("");
		lblWarning.setText("");
		endDateTextField.setText("");
		startDateTextField.setText("");
		categoryComboBox.setSelectedIndex(0);
		specificationComboBox.setSelectedIndex(0);
		driverLicenseTextField.setText("");
		totalTextField.setText("$ ");
		tableAvailableCars.setModel(new DefaultTableModel());
		customersTextField.setText("");
	}

	private void searchByLicense(long license) {
		customer = customerService.findByLicense(license);
		if (customer == null)
			customersTextField.setText("Customer not found!");

		customersTextField.setText(customer.toString());
	}

	public void populateComboBox() {
		specificationComboBox.removeAllItems();
		categoryComboBox.removeAllItems();
		categoryComboBox.addItem(null);
		specificationComboBox.addItem(null);

		for (Specification specification : specificationService.findAll()) {
			specificationComboBox.addItem(specification);
		}

		for (Category category : categoryService.findAll()) {
			categoryComboBox.addItem(category);
		}
	}

	private void searchCarsList() throws Exception {
		DefaultTableModel newTableModel = new DefaultTableModel();
		newTableModel.addColumn("Car Id");
		newTableModel.addColumn("Daily Rate");
		newTableModel.addColumn("Brand");
		newTableModel.addColumn("Name");
		newTableModel.addColumn("Plate");
		newTableModel.addColumn("Category");
		newTableModel.addColumn("Color");
		newTableModel.addColumn("Specifications");

		Date formattedStartDate = new Date(format.parse(startDateTextField.getText()).getTime());
		Date formattedEndDate = new Date(format.parse(endDateTextField.getText()).getTime());

		if (chckbxCategory.isSelected() && chckbxSpecifications.isSelected()) {
			populateTable(newTableModel,
					rentalService.findAvailable(formattedStartDate, formattedEndDate,
							(Category) categoryComboBox.getSelectedItem(),
							(Specification) specificationComboBox.getSelectedItem()));
		} else if (chckbxCategory.isSelected() && !chckbxSpecifications.isSelected()) {
			populateTable(newTableModel, rentalService.findAvailable(formattedStartDate, formattedEndDate,
					(Category) categoryComboBox.getSelectedItem()));
		} else if (chckbxSpecifications.isSelected() && !chckbxCategory.isSelected()) {
			populateTable(newTableModel, rentalService.findAvailable(formattedStartDate, formattedEndDate,
					(Specification) specificationComboBox.getSelectedItem()));
		} else if (!chckbxSpecifications.isSelected() && !chckbxCategory.isSelected()) {
			populateTable(newTableModel, rentalService.findAvailable(formattedStartDate, formattedEndDate));
		}

		tableAvailableCars.setModel(newTableModel);
	}

	private void checkBoxes() throws CredentialsException {
		if (chckbxCategory.isSelected() && categoryComboBox.getSelectedItem() == null)
			throw new CredentialsException("Please Enter a valid Category");
		if (chckbxSpecifications.isSelected() && specificationComboBox.getSelectedItem() == null)
			throw new CredentialsException("Please Enter a valid Specification");
	}

	private void createNewRental() throws Exception {
		Rental rental = new Rental();

		Car car = carService.findById(Long.parseLong(carIdTextField.getText()));
		if (car == null && customer == null)
			throw new IllegalArgumentException("Please select a valid car and customer");

		rental.setCar(car);
		rental.setCustomer(customer);
		rental.setStartDate(new Date(format.parse(startDateTextField.getText()).getTime()));
		rental.setEndDate(new Date(format.parse(endDateTextField.getText()).getTime()));
		rental.setTotal(Double.parseDouble(totalTextField.getText().replace("$", "")));

		rentalService.createRental(rental);
	}

	private void populateTable(DefaultTableModel model, List<Car> cars) {
		for (Car car : cars) {
			String specifications = getCarSpecifications(car);

			model.addRow(new Object[] { car.getId(), car.getDailyRate(), car.getBrand(), car.getName(),
					car.getLicensePlate(), car.getCategory(), car.getColor(), specifications });
		}
	}

	private String getCarSpecifications(Car car) {
		String specifications = "";

		for (CarSpecification carSpecification : carSpecificationService.getCarSpecifications(car)) {
			specifications += specifications.isEmpty() ? carSpecification.getSpecification().getName()
					: ", " + carSpecification.getSpecification().getName();
		}

		return specifications;
	}

	@SuppressWarnings("unused")
	private void checkDateFields() throws ParseException {
		try {
			Date formattedStartDate = new Date(format.parse(startDateTextField.getText()).getTime());
			Date formattedEndDate = new Date(format.parse(endDateTextField.getText()).getTime());
		} catch (Exception ex) {
			throw new ParseException("Enter a valid date! dd/MM/yyyy", 0);
		}
	}

	private void goToHomePage() {
		main.setPanel(main.getHomPane().getRootPanel());
	}
}