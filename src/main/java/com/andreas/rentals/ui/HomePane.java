package com.andreas.rentals.ui;

import javax.swing.JPanel;

public class HomePane extends JPanel{
 	
	private MainFrame main;
 	
	public HomePane(MainFrame frame) {
		main = (MainFrame) frame;
	}

	public JPanel getRootPanel() { 
		return this;
	}
}
