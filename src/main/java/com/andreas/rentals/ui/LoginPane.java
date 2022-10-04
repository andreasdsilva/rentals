package com.andreas.rentals.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import com.andreas.rentals.entities.User;
import com.andreas.rentals.exceptions.CredentialsException;
import com.andreas.rentals.services.UserService;
import com.andreas.rentals.util.ApplicationContext;
import com.andreas.rentals.util.BeanUtil;

@Component
public class LoginPane extends JPanel {
	private static final long serialVersionUID = 1L;
	
    //@Autowired
	private UserService userService;

	private MainFrame main;
	private JLabel loginLabel;
	private JTextField loginTextField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton registerButton;
	private JLabel invalidCredentials;

	public LoginPane( MainFrame frame ) {
		
		userService = (UserService) BeanUtil.getBeanByName("userService");
		this.setSize(720, 480);
		main = (MainFrame) frame;
		setLayout(null);
		
		loginLabel = new JLabel("Login:");
		loginLabel.setBounds(148, 107, 61, 34);
		add(loginLabel);
		
		loginTextField = new JTextField();
		loginTextField.setBounds(264, 107, 276, 34);
		add(loginTextField);
		loginTextField.setColumns(10);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(148, 185, 61, 34);
		add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(264, 185, 276, 34);
		add(passwordField);
		
		invalidCredentials = new JLabel();
		invalidCredentials.setBounds(201, 240, 287, 34);
		invalidCredentials.setForeground(Color.RED);
		invalidCredentials.setVisible(false);
		add(invalidCredentials);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(211, 288, 288, 47);
		add(loginButton);
		
		registerButton = new JButton("Register");
		registerButton.setBounds(211, 357, 288, 47);
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
        	String passwordText = new String(passwordField.getPassword());
        	String loginText =loginTextField.getText();
        	
        	if( loginText.isEmpty() || passwordText.isEmpty()) 
        		throw new CredentialsException( "All fields must be filled!" );
        	
            User user = userService.login( loginText, passwordText );            
            registerButton.setForeground( Color.BLACK );          
            ApplicationContext.getInstance().setLoggedUser( user );            
            main.getHomPane();
            main.setPanel( main.getHomPane().getRootPanel() );
            
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
