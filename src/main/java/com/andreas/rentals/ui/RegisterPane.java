package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.User;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.services.UserService;
import com.andreas.rentals.util.BeanUtil;

@Component
public class RegisterPane extends JPanel {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;
	
	private MainFrame main;
	private JTextField loginField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	
	public RegisterPane() {
		setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("    Register:");
		lblNewLabel_1.setBounds(230, 36, 220, 37);
		add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(230, 1, 220, 37);
		add(separator);
		
		JLabel lblNewLabel = new JLabel("    Login:");
		lblNewLabel.setBounds(95, 105, 220, 37);
		add(lblNewLabel);
		
		loginField = new JTextField();
		loginField.setBounds(376, 105, 220, 37);
		loginField.setHorizontalAlignment(SwingConstants.CENTER);
		add(loginField);
		loginField.setColumns(8);
		
		JLabel lblNewLabel_2 = new JLabel("    Password:");
		lblNewLabel_2.setBounds(95, 169, 220, 37);
		add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(376, 169, 220, 37);
		add(passwordField);
		
		JLabel lblNewLabel_3 = new JLabel("    Confirm password:");
		lblNewLabel_3.setBounds(95, 231, 220, 37);
		add(lblNewLabel_3);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(376, 231, 220, 37);
		add(confirmPasswordField);
		
		JLabel lblNewLabel_4 = new JLabel("Passwords must match!");
		lblNewLabel_4.setBounds(376, 286, 220, 37);
		lblNewLabel_4.setForeground(Color.RED);
		add(lblNewLabel_4);
		
		JButton btnNewButton_1 = new JButton("Register");
		btnNewButton_1.setBounds(95, 334, 236, 51);
		add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Already have an account");
		btnNewButton.setBounds(368, 334, 228, 51);
		add(btnNewButton);
	}

	
	public RegisterPane( MainFrame frame ) {
		this.userService = (UserService) BeanUtil.getBeanByName("userService");
		main = (MainFrame) frame;
		
		setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("    Register:");
		lblNewLabel_1.setBounds(230, 36, 220, 37);
		add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(230, 1, 220, 37);
		add(separator);
		
		JLabel lblNewLabel = new JLabel("    Login:");
		lblNewLabel.setBounds(95, 105, 220, 37);
		add(lblNewLabel);
		
		loginField = new JTextField();
		loginField.setBounds(376, 105, 220, 37);
		loginField.setHorizontalAlignment(SwingConstants.CENTER);
		add(loginField);
		loginField.setColumns(8);
		
		JLabel lblNewLabel_2 = new JLabel("    Password:");
		lblNewLabel_2.setBounds(95, 169, 220, 37);
		add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(376, 169, 220, 37);
		add(passwordField);
		
		JLabel lblNewLabel_3 = new JLabel("    Confirm password:");
		lblNewLabel_3.setBounds(95, 231, 220, 37);
		add(lblNewLabel_3);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(376, 231, 220, 37);
		add(confirmPasswordField);
		
		JLabel matchPasswordLabel = new JLabel("");
		matchPasswordLabel.setBounds(376, 286, 220, 37);
		matchPasswordLabel.setForeground(Color.RED);
		matchPasswordLabel.setVisible(false);
		add(matchPasswordLabel);
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(95, 334, 236, 51);
		add(registerButton);
		
		JButton alreadyHaveAccountButton = new JButton("Already have an account");
		alreadyHaveAccountButton.setBounds(368, 334, 228, 51);
		add(alreadyHaveAccountButton);
		
		alreadyHaveAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	matchPasswordLabel.setText("");
            	goToLogin();
            }
        });
		
		registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try 
            	{
            		String login = loginField.getText().toString();
            		if( login == null || login.isEmpty() ) throw new CredentialsException("Login must not be empty!");    
                	String password = checkPassword( passwordField, confirmPasswordField );
                	
                	matchPasswordLabel.setVisible(false);
            		createUser( login, password );
            		goToHome();
                	
            	}
            	catch( CredentialsException ex )
            	{
            		matchPasswordLabel.setText( ex.getMessage() );
            		matchPasswordLabel.setVisible(true);
            		main.setPanel(getRootPanel());
            	}
            }
        });

	}
	
	private String checkPassword( JPasswordField jpassword, JPasswordField jconfirmPassword ) {
		String password = new String( jpassword.getPassword() );
		String confirmPassword = new String( jconfirmPassword.getPassword() );
		
		if( password.equals(confirmPassword) )
			return password;
			
		throw new CredentialsException("Passwords must match!");
	}
	
	private void createUser( String login, String password) {
		User newUser = new User( login, password );
		userService.createUser(newUser);
	}
	
	private void goToLogin() {
		main.getLoginPane();
		main.setPanel( main.getLoginPane().getRootPanel() );
	}
	
	private void goToHome() {
		main.getHomPane();
		main.setPanel( main.getHomPane().getRootPanel() );
	}
	
	public JPanel getRootPanel() {
		return this;
	}
}
