package me.Chanadu.Chess;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChessBoardPanel extends JPanel {
	
	
	Color backgroundColor = new Color(0, 0, 0);
	Color lightColor = new Color(240, 217, 181);
	Color darkColor = new Color(181, 136, 99);
	
	JPanel[][] squares = new JPanel[8][8];
	Image chessPiecesPNG;
	
	
	ChessBoardPanel() {
		setPanel();
		createBoard();
		splitChessPiecesPNG();
		addPiecesToBoard();
	}
	
	
	private void setPanel() {
		this.setBackground(backgroundColor);
		this.setLayout(new GridLayout(8, 8, 0, 0));
		
	}
	
	
	private void createBoard() {
		Color color;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new JPanel();
				if ((i + j) % 2 == 0)
					color = lightColor;
				else
					color = darkColor;
				squares[i][j].setBackground(color);
				squares[i][j].setPreferredSize(new Dimension(80, 80));
				this.add(squares[i][j]);
			}
		}
	}
	
	
	private void splitChessPiecesPNG() {
		try {
			
			if (!(new File("me/Chanadu/Chess/Chess Pieces.png").exists())) {
				System.out.println("null");
			}
			
			
			chessPiecesPNG = new ImageIcon(this.getClass().getResource("/Chess Pieces.png")).getImage();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		squares[1][1].add(new JLabel(new ImageIcon(chessPiecesPNG.getScaledInstance(72, 72, Image.SCALE_SMOOTH))));
	}
	
	
	private void addPiecesToBoard() {
	
	}
}
