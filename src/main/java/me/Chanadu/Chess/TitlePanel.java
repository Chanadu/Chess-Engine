package me.Chanadu.Chess;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
	
	JLabel title = new JLabel();
	
	
	TitlePanel() {
		setUpTitleLabel();
		add(title);
	}
	
	
	private void setUpTitleLabel() {
		title.setText("Chess");
		title.setFont(new Font("Arial", Font.BOLD, 50));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
	}
	
	
	public void setTitle(String text) {
		title.setText(text);
	}
}
