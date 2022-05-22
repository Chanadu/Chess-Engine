package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardSquarePanel extends JPanel {
	
	
	int squareSize = 85;
	ChessBoardPanel boardPanel;
	Color startingColor;
	int xPos;
	int yPos;
	
	boolean isMoving = false;
	
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
	
	Color movableSquareColor = new Color(59, 108, 73);
	Color movingSquareColor = new Color(149, 207, 167);
	
	
	public BoardSquarePanel( int x, int y, Color color, ChessBoardPanel boardPanel) {
		this.boardPanel = boardPanel;
		xPos = x;
		yPos = y;
		startingColor = color;
		
		setLayout(new BorderLayout());
		setBackground(color);
		setPreferredSize(new Dimension(squareSize, squareSize));
		
		addMouseAdapter();
	}
	
	
	public void addMouseAdapter() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//MOVABLE SQUARE CODE
				if (getBackground().equals(movableSquareColor)) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (boardPanel.getSquares()[i][j].getMoving()) {
								BoardSquarePanel movingSquare = boardPanel.getSquares()[i][j];
								Board board = boardPanel.getBoard();
								Piece piece = board.getPiece(strToSquares[i][j]);
								String pieceName = piece.name().toLowerCase();
								Image pieceImage = boardPanel.getPiecesStringToImage().get(pieceName);
								
								board.doMove(new Move(strToSquares[i][j],
										strToSquares[xPos][yPos]));
								
								movingSquare.removeAll();
								removeAll();
								
								if (!pieceName.equalsIgnoreCase("none")) {
									add(new GUIPiece(new ImageIcon(pieceImage)));
								}
								
								revalidate();
								repaint();
								movingSquare.revalidate();
								movingSquare.repaint();
								
								boardPanel.getSquares()[i][j].isMoving = false;
								boardPanel.reDrawBoard();
								break;
							}
						}
					}
				}
				
				else if (getBackground().equals(startingColor)) {
					
					boardPanel.reDrawBoard();
					
					isMoving = true;
					
					JPanel[][] squares = boardPanel.getSquares();
					Square currentSquareAN = strToSquares[xPos][yPos];
					List<Move> legalMoves = boardPanel.getBoard().legalMoves();
					
					setBackground(movingSquareColor);
 				
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (legalMoves.contains(new Move(currentSquareAN, strToSquares[i][j]))) {
								squares[i][j].setBackground(movableSquareColor);
								squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 5));
							}
						}
					}
					
				}
			}
		});
	}
	
	
	public boolean getMoving() {
		return isMoving;
	}
	
	
	public void setMoving(boolean moving) {
		isMoving = moving;
	}
}
