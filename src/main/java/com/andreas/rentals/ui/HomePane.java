package com.andreas.rentals.ui;

import javax.swing.JPanel;

public class HomePane extends JPanel {

private static final long serialVersionUID = 1L;
	
	private MainFrame main;
 	
	public HomePane(MainFrame frame) {
		setMain((MainFrame) frame);
	}

	public JPanel getRootPanel() { 
		return this;
	}

	public MainFrame getMainFrame() {
		return main;
	}

	private void setMain(MainFrame main) {
		this.main = main;
	}
}
