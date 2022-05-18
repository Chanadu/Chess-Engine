package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class GUIPiece extends JLabel implements MouseListener {
	
	
	Color lightColor = new Color(240, 217, 181);
	Color darkColor = new Color(181, 136, 99);
	
	String pieceName;
	ImageIcon pieceImage;
	ChessBoardPanel boardPanel;
	Board board;
	
	int rowNum;
	int colNum;
	
	Square[][] strToSquares = {
			{
					Square.A8, Square.B8, Square.C8, Square.D8, Square.E8, Square.F8, Square.G8, Square.H8
			}, {
			Square.A7, Square.B7, Square.C7, Square.D7, Square.E7, Square.F7, Square.G7, Square.H7
	}, {
			Square.A6, Square.B6, Square.C6, Square.D6, Square.E6, Square.F6, Square.G6, Square.H6
	}, {
			Square.A5, Square.B5, Square.C5, Square.D5, Square.E5, Square.F5, Square.G5, Square.H5
	}, {
			Square.A4, Square.B4, Square.C4, Square.D4, Square.E4, Square.F4, Square.G4, Square.H4
	}, {
			Square.A3, Square.B3, Square.C3, Square.D3, Square.E3, Square.F3, Square.G3, Square.H3
	}, {
			Square.A2, Square.B2, Square.C2, Square.D2, Square.E2, Square.F2, Square.G2, Square.H2
	}, {
			Square.A1, Square.B1, Square.C1, Square.D1, Square.E1, Square.F1, Square.G1, Square.H1
	}
	};
	
	GUIPiece(String pieceName, ImageIcon pieceImage, ChessBoardPanel boardPanel, Board board, int rowNum, int colNum) {
		
		this.pieceName = pieceName;
		this.pieceImage = pieceImage;
		this.boardPanel = boardPanel;
		this.board = board;
		this.rowNum = rowNum;
		this.colNum = colNum;
		
		createPiece();
	}
	
	
	private void reDrawBoard() {
		Color color;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					color = lightColor;
				}
				else {
					color = darkColor;
				}
				boardPanel.getSquares()[i][j].setBorder(new EmptyBorder(0, 0, 0,0 ));
				boardPanel.getSquares()[i][j].setBackground(color);
			}
		}

	}
	
	
	private void createPiece() {
		addMouseListener(this);
		this.setIcon(pieceImage);
		this.setBorder(new CompoundBorder(this.getBorder(), new EmptyBorder(5, 0, 0, 0)));
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}
	
	
	@Override
	public void mousePressed(MouseEvent evt) {
		//System.out.println("..");
		reDrawBoard();
		
		JPanel[][] squares = boardPanel.getSquares();
		
		Square currentSquare = strToSquares[rowNum][colNum];
		
		List<Move> legalMoves = board.legalMoves();
		
		squares[rowNum][colNum].setBackground(new Color(149, 207, 167));
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (legalMoves.contains(new Move(currentSquare, strToSquares[i][j]))) {
					squares[i][j].setBackground(new Color(59, 108, 73));
					squares[i][j].setBorder(new LineBorder(new Color(0, 0,0), 5));
				}
			}
		}
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
	
	}
	
	
	@Override
	public void mouseExited(MouseEvent e) {
	
	}
	
}
