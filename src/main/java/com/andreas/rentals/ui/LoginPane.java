package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.User;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.services.UserService;
import com.andreas.rentals.util.ApplicationContext;

@Component
public class LoginPane extends JPanel {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	private MainFrame main;
	private JLabel loginLabel;
	private JTextField loginTextField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton registerButton;
	private JLabel invalidCredentials;
	
	
	public LoginPane() {
		setLayout(new GridLayout(7, 1, 0, 10));
		
		JLabel lblNewLabel = new JLabel("Login");
		add(lblNewLabel);
		
		loginTextField = new JTextField();
		add(loginTextField);
		loginTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("Invalid login or password!");
		lblNewLabel_2.setForeground(Color.RED);
		add(lblNewLabel_2);
		
		JButton btnNewButton_2 = new JButton("Login");
		add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("Register");
		add(btnNewButton_1);}

	public LoginPane( MainFrame frame ) {
		
		//userService = UserService.getInstance();
		
		main = (MainFrame) frame;
		
		setLayout(new GridLayout(7, 1, 0, 10));
		
		loginLabel = new JLabel("Login");
		add(loginLabel);
		
		loginTextField = new JTextField();
		add(loginTextField);
		loginTextField.setColumns(10);
		
		passwordLabel = new JLabel("Password");
		add(passwordLabel);
		
		passwordField = new JPasswordField();
		add(passwordField);
		
		invalidCredentials = new JLabel();
		invalidCredentials.setForeground(Color.RED);
		invalidCredentials.setVisible(false);
		add(invalidCredentials);
		
		loginButton = new JButton("Login");
		add(loginButton);
		
		registerButton = new JButton("Register");
		add(registerButton);
		
		registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	goToRegister();
            }
        });
		
		loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
	}
	
	private void performLogin() {
        try {
        	if( loginTextField.getText().isEmpty() || passwordField.getPassword().toString().isEmpty()) 
        		throw new CredentialsException( "All fields must be filled!" );
        	
            User user = userService.login( loginTextField.getText(), passwordField.getPassword().toString() );

            registerButton.setForeground( Color.BLACK );
            ApplicationContext.getInstance().setLoggedUser( user );
//            main.getWelcomePane().initCards();
//            main.setPanel( main.getWelcomePane().getRootPanel() );
            passwordField.setText("");
            loginTextField.setText("");
        }
        catch ( CredentialsException ex ) {
        	invalidCredentials.setText( ex.getMessage() );
        	invalidCredentials.setVisible(true);
        }
    }
	
	private void goToRegister() {
		main.getRegisterPane();
		main.setPanel( main.getRegisterPane().getRootPanel() );
	}

	public JPanel getRootPanel() {
		return this;
	}
}
