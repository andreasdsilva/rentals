package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.Rental;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.services.RentalService;
import com.andreas.rentals.util.BeanUtil;

@Component
public class ConsultRentalsPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private RentalService rentalService;
	private MainFrame main;
	private JTable tableRentals;
	private JFormattedTextField startDateTextField;
	private JFormattedTextField endDateTextField;
	private JFormattedTextField updateEndDateTextField;
	private JFormattedTextField updateStartDateTextField;
	private JFormattedTextField rentalIdTextField;
	private JLabel lblWarning;

	Date today = new Date(Calendar.getInstance().getTime().getTime());
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	DateFormatter df = new DateFormatter(format);

	public ConsultRentalsPane(MainFrame frame) {
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
		this.main = main;
		setLayout(null);

		JLabel lblConsultRentals = new JLabel("Consult Rentals:");
		lblConsultRentals.setFont(new Font("Arial", Font.BOLD, 15));
		lblConsultRentals.setBounds(315, 0, 219, 95);
		add(lblConsultRentals);

		JButton btnHomePage = new JButton("Home Page");
		btnHomePage.setBounds(548, 11, 137, 31);
		add(btnHomePage);

		JLabel lblStartDate = new JLabel("Start:");
		lblStartDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblStartDate.setBounds(121, 75, 90, 42);
		add(lblStartDate);

		startDateTextField = new JFormattedTextField(df);
		startDateTextField.setBounds(159, 83, 71, 26);
		startDateTextField.setValue(today);
		add(startDateTextField);
		startDateTextField.setColumns(10);

		JLabel lblEndDate = new JLabel("End:");
		lblEndDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndDate.setBounds(240, 75, 90, 42);
		add(lblEndDate);

		endDateTextField = new JFormattedTextField(df);
		endDateTextField.setColumns(10);
		endDateTextField.setBounds(280, 83, 78, 26);
		endDateTextField.setValue(today);
		add(endDateTextField);

		JLabel dateFormatTextField = new JLabel("(dd/MM/yyyy)");
		dateFormatTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		dateFormatTextField.setBounds(368, 75, 90, 42);
		add(dateFormatTextField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 120, 655, 223);
		add(scrollPane);

		tableRentals = new JTable();
		scrollPane.setViewportView(tableRentals);

		lblWarning = new JLabel("");
		lblWarning.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWarning.setForeground(Color.RED);
		lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarning.setBounds(87, 399, 554, 31);
		add(lblWarning);

		JLabel lblConsultDates = new JLabel("Rental dates:");
		lblConsultDates.setFont(new Font("Arial", Font.PLAIN, 12));
		lblConsultDates.setBounds(30, 75, 90, 42);
		add(lblConsultDates);

		JButton btnFilterRentals = new JButton("Filter Rentals");
		btnFilterRentals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					filterRentalsByDate();
				} catch (ParseException ex) {
					lblWarning.setText("Please enter a valid date! (dd/MM/yyyy)");
				}
			}
		});
		btnFilterRentals.setBounds(548, 81, 137, 31);
		add(btnFilterRentals);

		JLabel lblUpdateStart = new JLabel("Start:");
		lblUpdateStart.setFont(new Font("Arial", Font.PLAIN, 12));
		lblUpdateStart.setBounds(227, 354, 90, 42);
		add(lblUpdateStart);

		updateStartDateTextField = new JFormattedTextField((AbstractFormatter) null);
		updateStartDateTextField.setColumns(10);
		updateStartDateTextField.setValue(today);
		updateStartDateTextField.setBounds(269, 362, 71, 26);
		add(updateStartDateTextField);

		JLabel lblUpdateEnd = new JLabel("End:");
		lblUpdateEnd.setFont(new Font("Arial", Font.PLAIN, 12));
		lblUpdateEnd.setBounds(346, 354, 90, 42);
		add(lblUpdateEnd);

		updateEndDateTextField = new JFormattedTextField((AbstractFormatter) null);
		updateEndDateTextField.setColumns(10);
		updateEndDateTextField.setValue(today);
		updateEndDateTextField.setBounds(380, 362, 78, 26);
		add(updateEndDateTextField);

		JLabel dateFormatTextField_1 = new JLabel("(dd/MM/yyyy)");
		dateFormatTextField_1.setFont(new Font("Arial", Font.PLAIN, 12));
		dateFormatTextField_1.setBounds(469, 354, 90, 42);
		add(dateFormatTextField_1);

		JLabel lblUpdateDates = new JLabel("Update dates:");
		lblUpdateDates.setFont(new Font("Arial", Font.PLAIN, 12));
		lblUpdateDates.setBounds(140, 354, 81, 42);
		add(lblUpdateDates);

		JButton btnUpdateRental = new JButton("Update Rental");
		btnUpdateRental.setBounds(548, 360, 137, 31);
		add(btnUpdateRental);

		btnUpdateRental.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Rental rental = rentalService.findById(Long.parseLong(rentalIdTextField.getText()));
					if (rental == null)
						throw new CredentialsException("Rental not found!");

					if (canUpdate(rental)) {
						updateRental(rental);
						filterRentalsByDate();
						lblWarning.setText("Rental updated!");
						
					} else {
						lblWarning.setText("Rental must be before start to update!");
					}
				} catch (ParseException | NumberFormatException ex) {
					lblWarning.setText("Please enter a valid date! (dd/MM/yyyy)");
				} catch (Exception ex) {
					lblWarning.setText(ex.getMessage());
				}
			}
		});

		JLabel lblRentalId = new JLabel("Id:");
		lblRentalId.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRentalId.setBounds(30, 354, 90, 42);
		add(lblRentalId);

		rentalIdTextField = new JFormattedTextField((Object) null);
		rentalIdTextField.setColumns(10);
		rentalIdTextField.setBounds(52, 362, 60, 26);
		add(rentalIdTextField);

		btnHomePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToHomePage();
				clearFields();
			}
		});
	}

	private boolean canUpdate(Rental rental) {
		return !rental.getStartDate().equals(today) || !rental.getEndDate().equals(today)
				|| !rental.getEndDate().before(today)
				|| !(rental.getStartDate().after(today) && rental.getEndDate().after(today));
	}

	private void updateRental(Rental rental) throws Exception {

		Date formattedStartDate = new Date(format.parse(updateStartDateTextField.getText()).getTime());
		Date formattedEndDate = new Date(format.parse(updateEndDateTextField.getText()).getTime());
		long diff = formattedEndDate.getTime() - formattedStartDate.getTime();
		float days = (diff / (1000 * 60 * 60 * 24));

		rental.setStartDate(formattedStartDate);
		rental.setEndDate(formattedEndDate);
		rental.setTotal(days * rental.getCar().getDailyRate());

		rentalService.updateRental(rental);
	}

	private void filterRentalsByDate() throws ParseException {
		List<Rental> rentals = rentalService.findByDates(new Date(format.parse(startDateTextField.getText()).getTime()),
				new Date(format.parse(endDateTextField.getText()).getTime()));
		if (!rentals.isEmpty()) {
			tableRentals.setModel(new DefaultTableModel());
			
			populateTable(rentals);
			lblWarning.setText("");
		} else {
			lblWarning.setText("No rentals found for the specified dates!");
		}
	}

	private void populateTable(List<Rental> rentals) {
		DefaultTableModel newTableModel = new DefaultTableModel();
		newTableModel.addColumn("Id");
		newTableModel.addColumn("Customer");
		newTableModel.addColumn("Car");
		newTableModel.addColumn("Star Date");
		newTableModel.addColumn("End Date");
		newTableModel.addColumn("Total");

		for (Rental rental : rentals) {
			newTableModel.addRow(new Object[] { rental.getId(), rental.getCustomer(), rental.getCar(),
					rental.getStartDate(), rental.getEndDate(), rental.getTotal() });
		}

		tableRentals.setModel(newTableModel);
	}

	private void clearFields() {
		startDateTextField.setText("");
		endDateTextField.setText("");
		updateEndDateTextField.setText("");
		updateStartDateTextField.setText("");
		rentalIdTextField.setText("");
		tableRentals.setModel(new DefaultTableModel());
	}

	private void goToHomePage() {
		main.getHomPane();
		main.setPanel(main.getHomPane().getRootPanel());
	}
}