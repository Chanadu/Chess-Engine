package me.Chanadu.Chess;

import javax.swing.*;
import java.awt.*;

public class ChessGUI extends JFrame {
	
	
	Color backgroundColor = new Color(206, 1, 1);
	
	ChessBoardPanel boardPanel;
	UndoPanel undoPanel;
	
	ChessGUI() {
		setJFrame();
		this.setVisible(true);
	}
	
	
	private void setJFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 800);
		this.getContentPane().setBackground(backgroundColor);
		this.setTitle("Chess");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		boardPanel = new ChessBoardPanel(this);
		undoPanel = new UndoPanel(boardPanel);
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(undoPanel, BorderLayout.SOUTH);
	}
	
}
