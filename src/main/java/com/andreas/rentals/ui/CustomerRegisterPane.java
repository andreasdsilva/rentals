package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DateFormatter;

import com.andreas.rentals.entities.Customer;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.services.CustomerService;
import com.andreas.rentals.util.BeanUtil;

public class CustomerRegisterPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private CustomerService customerService;
	private MainFrame main;

	private JTextField nameTextField;
	private JLabel customerWarningLabel;
	private JTextField emailTextField;
	private JTextField addressTextField;
	private JFormattedTextField driveLicenseTextField;
	private JFormattedTextField birthDateTextField;
	private JFormattedTextField phoneTextField;

	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public CustomerRegisterPane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() {
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {
		customerService = (CustomerService) BeanUtil.getBeanByName("customerService");
		this.main = main;
		setLayout(null);

		JLabel newBrandLabel = new JLabel("New Customer:");
		newBrandLabel.setFont(new Font("Arial", Font.BOLD, 15));
		newBrandLabel.setBounds(291, 0, 219, 129);
		add(newBrandLabel);

		JLabel brandNameLabel = new JLabel("Name*:");
		brandNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		brandNameLabel.setBounds(51, 135, 99, 42);
		add(brandNameLabel);

		nameTextField = new JTextField();
		nameTextField.setBounds(127, 140, 271, 31);
		add(nameTextField);
		nameTextField.setColumns(10);

		customerWarningLabel = new JLabel("");
		customerWarningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		customerWarningLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		customerWarningLabel.setForeground(Color.RED);
		customerWarningLabel.setVisible(true);
		customerWarningLabel.setBounds(241, 371, 247, 23);
		add(customerWarningLabel);

		JButton registerCustomerBtn = new JButton("Register Customer");
		registerCustomerBtn.setBounds(264, 405, 206, 31);
		add(registerCustomerBtn);

		JButton btnHomePage = new JButton("Home Page");
		btnHomePage.setBounds(573, 0, 137, 31);
		add(btnHomePage);

		JLabel customerDescriptionLabel = new JLabel("E-mail:");
		customerDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		customerDescriptionLabel.setBounds(51, 215, 89, 42);
		add(customerDescriptionLabel);

		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(127, 221, 271, 31);
		add(emailTextField);

		JLabel lblAddress = new JLabel("Address*:");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAddress.setBounds(51, 294, 89, 42);
		add(lblAddress);

		addressTextField = new JTextField();
		addressTextField.setColumns(10);
		addressTextField.setBounds(127, 305, 271, 31);
		add(addressTextField);

		DateFormatter df = new DateFormatter(format);

		birthDateTextField = new JFormattedTextField(df);
		birthDateTextField.setBounds(548, 141, 137, 31);
		birthDateTextField.setPreferredSize(new Dimension(100, 20));
		birthDateTextField.setValue(new Date(Calendar.getInstance().getTime().getTime()));
		add(birthDateTextField);

		JLabel lblBirthDate = new JLabel("Birth Date*:");
		lblBirthDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBirthDate.setBounds(439, 135, 99, 42);
		add(lblBirthDate);

		driveLicenseTextField = new JFormattedTextField();
		driveLicenseTextField.setBounds(548, 221, 137, 31);
		add(driveLicenseTextField);

		JLabel lblDriverLicense = new JLabel("Driver License*:");
		lblDriverLicense.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDriverLicense.setBounds(439, 215, 99, 42);
		add(lblDriverLicense);

		phoneTextField = new JFormattedTextField();
		phoneTextField.setBounds(548, 300, 137, 31);
		add(phoneTextField);

		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPhoneNumber.setBounds(439, 294, 99, 42);
		add(lblPhoneNumber);

		registerCustomerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Start");
					String name = nameTextField.getText().toString();
					String address = addressTextField.getText().toString();
					String birthDate = birthDateTextField.getText().toString();
					String driverLicense = driveLicenseTextField.getText().toString();

					boolean canCreate = checkRequiredFields(name, address, birthDate, driverLicense);
					System.out.println(canCreate + " can create");
					if (canCreate) {
						Customer customer = new Customer();
						
						customer.setAddress(address);
						customer.setName(name);
						customer.setPhoneNumber(Integer.parseInt(phoneTextField.getText().toString()));
						customer.setBirthDate(new Date(format.parse(birthDate).getTime()));
						customer.setEmail(emailTextField.getText().toString());
						customer.setDriverLicense(Integer.parseInt(driverLicense));

						createNewCustomer(customer);
					}
				} catch (CredentialsException ex) {
					customerWarningLabel.setText(ex.getMessage());
				} catch (Exception ex) {
					customerWarningLabel.setText(ex.getMessage());
					System.out.println(ex.getMessage());
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

	private boolean checkRequiredFields(String name, String address, String birthDate, String license)
			throws CredentialsException {

		if (name.isEmpty() || address.isEmpty() || birthDate.isEmpty() || license.isEmpty())
			throw new CredentialsException("Please fill the required fields! '*'");

		if (checkDate( birthDate ) && checkLicense(license)) {
			return true;
		}
		
		return false;
	}

	private boolean checkDate(String date) {
		try {
			Date formattedDate = new Date(format.parse(date).getTime());
			System.out.println(formattedDate);
			return true;
		} catch (Exception ex) {
			throw new CredentialsException("Enter a valid date! dd/MM/yyyy");
		}
	}
	
	private boolean checkLicense(String license) {
		try {
			@SuppressWarnings("unused")
			int parsedLicense = Integer.parseInt(license);
			return true;
		} catch (Exception ex) {
			throw new CredentialsException("Enter a valid driver license!");
		}
	}
	
	private void clearFields() {
		customerWarningLabel.setText("");
		nameTextField.setText("");
		emailTextField.setText("");
		birthDateTextField.setText("");
		addressTextField.setText("");
		phoneTextField.setText("");
	}

	private void goToHomePage() {
		main.setPanel(main.getHomPane().getRootPanel());
	}

	private void createNewCustomer(Customer customer) {
		Customer existingCustomer = customerService.findByLicense(customer.getDriverLicense());
		System.out.println(existingCustomer + " exe");
		if (existingCustomer == null) {
			customerService.createCustomer(customer);
			customerWarningLabel.setText("Customer " + customer + " created!");
		} else {
			customerWarningLabel.setText("Customer already exists!");
		}
		main.setPanel(getRootPanel());

	}
}
