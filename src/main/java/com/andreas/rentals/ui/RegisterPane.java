package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.GridLayout;
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

	;;@Autowired
	private UserService userService;
	
	private MainFrame main;
	private JTextField loginField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	
	public RegisterPane() {
		setLayout(new GridLayout(6, 2, 10, 15));
		
		JLabel lblNewLabel_1 = new JLabel("    Register:");
		add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		add(separator);
		
		JLabel lblNewLabel = new JLabel("    Login:");
		add(lblNewLabel);
		
		loginField = new JTextField();
		loginField.setHorizontalAlignment(SwingConstants.CENTER);
		add(loginField);
		loginField.setColumns(8);
		
		JLabel lblNewLabel_2 = new JLabel("    Password:");
		add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		add(passwordField);
		
		JLabel lblNewLabel_3 = new JLabel("    Confirm password:");
		add(lblNewLabel_3);
		
		confirmPasswordField = new JPasswordField();
		add(confirmPasswordField);
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1);
		
		JLabel lblNewLabel_4 = new JLabel("Passwords must match!");
		lblNewLabel_4.setForeground(Color.RED);
		add(lblNewLabel_4);
		
		JButton btnNewButton_1 = new JButton("Register");
		add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Already have an account");
		add(btnNewButton);
	}

	
	public RegisterPane( MainFrame frame ) {
		this.userService = (UserService) BeanUtil.getBeanByName("userService");
		main = (MainFrame) frame;
		
		setLayout(new GridLayout(6, 2, 10, 15));
		
		JLabel registerLabel = new JLabel("    Register:");
		add(registerLabel);
		
		JSeparator separator = new JSeparator();
		add(separator);
		
		JLabel loginLabel = new JLabel("    Login:");
		add(loginLabel);
		
		loginField = new JTextField();
		add(loginField);
		loginField.setColumns(8);
		
		JLabel passwordLabel = new JLabel("    Password:");
		add(passwordLabel);
		
		passwordField = new JPasswordField();
		add(passwordField);
		
		JLabel confirmPasswordLabel = new JLabel("    Confirm password:");
		add(confirmPasswordLabel);
		
		confirmPasswordField = new JPasswordField();
		add(confirmPasswordField);
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1);
		
		JLabel matchPasswordLabel = new JLabel("");
		matchPasswordLabel.setForeground(Color.RED);
		matchPasswordLabel.setVisible(false);
		add(matchPasswordLabel);
		
		JButton registerButton = new JButton("Register");
		add(registerButton);
		
		JButton alreadyHaveAccountButton = new JButton("Already have an account");
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
