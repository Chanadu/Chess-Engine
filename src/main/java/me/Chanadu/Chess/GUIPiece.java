package me.Chanadu.Chess;


import javax.swing.*;


public class GUIPiece extends JLabel{
	GUIPiece(ImageIcon pieceImage) {
		setIcon(pieceImage);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
	}
}
