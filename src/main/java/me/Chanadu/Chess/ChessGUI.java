package me.Chanadu.Chess;

import javax.swing.*;
import java.awt.*;

public class ChessGUI extends JFrame {
	
	
	Color backgroundColor = new Color(206, 1, 1);
	
	ChessBoardPanel boardPanel;
	UndoPanel undoPanel;
	TitlePanel titlePanel;
	
	ChessGUI() {
		setJFrame();
		this.setVisible(true);
	}
	
	
	private void setJFrame() {
		
		//Set up the JFrame
		this.setTitle("Chess");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBackground(backgroundColor);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		titlePanel = new TitlePanel();
		boardPanel = new ChessBoardPanel(this, titlePanel);
		undoPanel = new UndoPanel(boardPanel);
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(undoPanel, BorderLayout.SOUTH);
		this.add(titlePanel, BorderLayout.NORTH);
	}
	
}
