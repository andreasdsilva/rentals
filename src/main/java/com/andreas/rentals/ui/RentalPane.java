package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.Car;
import com.andreas.rentals.entities.Category;
import com.andreas.rentals.entities.Customer;
import com.andreas.rentals.entities.Rental;
import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.services.CarService;
import com.andreas.rentals.services.CategoryService;
import com.andreas.rentals.services.CustomerService;
import com.andreas.rentals.services.RentalService;
import com.andreas.rentals.services.SpecificationService;
import com.andreas.rentals.util.BeanUtil;

@Component
public class RentalPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private RentalService rentalService;
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
	private JLabel lblError;
	private JComboBox<Category> categoryComboBox;
	private JComboBox<Specification> specificationComboBox;
	private JTable carListTable;
	private JCheckBox chckbxCategory;
	private JCheckBox chckbxSpecifications;

	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

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
		carService = (CarService) BeanUtil.getBeanByName("carService");
		specificationService = (SpecificationService) BeanUtil.getBeanByName("specificationService");
		categoryService = (CategoryService) BeanUtil.getBeanByName("categoryService");
		customerService = (CustomerService) BeanUtil.getBeanByName("customerService");

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
				} catch (Exception ex) {
					lblError.setText("Please enter a valid license, only numbers!");
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
		lblTotal.setBounds(397, 372, 90, 42);
		add(lblTotal);

		totalTextField = new JTextField();
		totalTextField.setText("$");
		totalTextField.setEnabled(false);
		totalTextField.setColumns(10);
		totalTextField.setBounds(443, 380, 116, 26);
		add(totalTextField);

		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Arial", Font.PLAIN, 14));
		lblError.setBounds(165, 411, 387, 26);
		add(lblError);

		JButton btnRentNow = new JButton("Rent Now");
		btnRentNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewRental();
			}
		});
		btnRentNow.setBounds(569, 378, 116, 31);
		add(btnRentNow);

		JLabel dateFormatTextField = new JLabel("(dd/MM/yyyy)");
		dateFormatTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		dateFormatTextField.setBounds(332, 76, 90, 42);
		add(dateFormatTextField);

		carListTable = new JTable();
		carListTable.setCellSelectionEnabled(true);
		carListTable.setColumnSelectionAllowed(true);
		carListTable.setBounds(32, 328, 645, -203);
		add(carListTable);

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
				if(event.getStateChange()==ItemEvent.SELECTED) {					
					categoryComboBox.setEnabled(true);
				}
				else {
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
				if(event.getStateChange()==ItemEvent.SELECTED) {					
					specificationComboBox.setEnabled(true);
				}
				else {
					specificationComboBox.setEnabled(true);
				}
			}
		});

		JButton btnFilter = new JButton("Filter");
		btnFilter.setBounds(397, 341, 288, 32);
		add(btnFilter);

		btnFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					checkDateFields();
					searchCarsList();
				} catch (Exception ex) {
					lblError.setText(ex.getMessage());
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

	private void searchByLicense(long license) {
		customer = customerService.findByLicense(license);
		if (customer == null)
			customersTextField.setText("Customer not found!");

		customersTextField.setText(customer.toString());
	}

	public void populateComboBox() {
		specificationComboBox.removeAll();
		categoryComboBox.removeAll();
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
		Date formattedStartDate = new Date(format.parse(startDateTextField.getText()).getTime());
		Date formattedEndDate = new Date(format.parse(endDateTextField.getText()).getTime());
		if (chckbxCategory.isSelected() && chckbxSpecifications.isSelected()) {
			populateTable( carService.findAvailable(formattedStartDate, formattedEndDate) );
		} else if (chckbxCategory.isSelected() && !chckbxSpecifications.isSelected()) {
			populateTable( carService.findAvailable(formattedStartDate, formattedEndDate) );
		} else if (chckbxSpecifications.isSelected() && !chckbxCategory.isSelected()) {
			populateTable( carService.findAvailable(formattedStartDate, formattedEndDate) );
		} else if (!chckbxSpecifications.isSelected() && !chckbxCategory.isSelected()) {
			populateTable( carService.findAvailable(formattedStartDate, formattedEndDate ) );
		}
	}

	private void createNewRental() {
		Rental rental = new Rental();
		rentalService.createRental(rental);
	}
	
	private void populateTable(List<Car> cars) {

	}

	private void checkDateFields() {
		try {
			Date formattedStartDate = new Date(format.parse(startDateTextField.getText()).getTime());
			System.out.println(formattedStartDate);
			Date formattedEndDate = new Date(format.parse(endDateTextField.getText()).getTime());
			System.out.println(formattedEndDate);
		} catch (Exception ex) {
			throw new CredentialsException("Enter a valid date! dd/MM/yyyy");
		}
	}

	private void goToHomePage() {
		main.setPanel(main.getHomPane().getRootPanel());
	}
}