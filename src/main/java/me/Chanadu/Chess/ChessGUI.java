package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;

import javax.swing.*;
import java.awt.*;

public class ChessGUI extends JFrame {
	
	
	Color backgroundColor = new Color(206, 1, 1);
	
	ChessBoardPanel boardPanel;
	Board currentBoard;
	
	
	ChessGUI(Board board) {
		this.currentBoard = board;
		setJFrame();
		this.setVisible(true);
	}
	
	
	private void setJFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(720, 720);
		this.getContentPane().setBackground(backgroundColor);
		this.setTitle("Chess");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		boardPanel = new ChessBoardPanel();
		this.add(boardPanel, BorderLayout.CENTER);
	}
	
	
}
