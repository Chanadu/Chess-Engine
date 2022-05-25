package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.*;
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
	
	
	public BoardSquarePanel(int x, int y, Color color, ChessBoardPanel boardPanel) {
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
								//Variables
								Board board = boardPanel.getBoard();
								Piece piece = board.getPiece(strToSquares[i][j]);
								String pieceName = piece.name().toLowerCase();
								
								board.doMove(new Move(strToSquares[i][j], strToSquares[xPos][yPos]));
								
								//Reload Moving and toMove Square
								reloadSquare(i, j);
								reloadSquare(xPos, yPos);
								
								//En-Passant
								if (pieceName.contains("pawn")) {
									if (j != 0) {
										reloadSquare(i, j - 1);
									}
									if (j != 7) {
										reloadSquare(i, j + 1);
									}
								}
								
								//Castling
								castlingMovement(i, j);
								
								//Promotion
								promotionMovement();
								
								//GameStates
								checkGameStates();
								
								//Remove Board Panel Background Colors
								boardPanel.getSquares()[i][j].isMoving = false;
								boardPanel.reDrawBoard();
								
								
								break;
							}
						}
					}
					
				} else if (getBackground().equals(startingColor)) {
					boardPanel.reDrawBoard();
					
					isMoving = true;
					
					JPanel[][] squares = boardPanel.getSquares();
					Square currentSquareAN = strToSquares[xPos][yPos];
					List<Move> legalMoves = boardPanel.getBoard().legalMoves();
					
					setBackground(movingSquareColor);
					
					Board board = boardPanel.getBoard();
					
					PieceType pieceType = board.getPiece(currentSquareAN).getPieceType();
					
					if (pieceType == null) {
						return;
					}
					
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (legalMoves.contains(new Move(currentSquareAN, strToSquares[i][j]))) {
								squares[i][j].setBackground(movableSquareColor);
								squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 5));
							}
						}
					}
					
					if (pieceType.equals(PieceType.PAWN)) {
						int x = -1;
						if (board.getSideToMove().equals(Side.WHITE) && xPos == 1 && board.getPiece(currentSquareAN).getPieceSide().equals(Side.WHITE)) {
							x = 0;
						} else if (board.getSideToMove().equals(Side.BLACK) && xPos == 6 && board.getPiece(currentSquareAN).getPieceSide().equals(Side.BLACK)) {
							x = 7;
						}
						
						if (x != -1) {
							if (board.getPiece(strToSquares[x][yPos]).name().equalsIgnoreCase("none")) {
								squares[x][yPos].setBackground(movableSquareColor);
								squares[x][yPos].setBorder(BorderFactory.createLineBorder(Color.black, 5));
							}
							
							if (yPos - 1 != -1) {
								if (!board.getPiece(strToSquares[x][yPos - 1]).name().equalsIgnoreCase("none")) {
									squares[x][yPos - 1].setBackground(movableSquareColor);
									squares[x][yPos - 1].setBorder(BorderFactory.createLineBorder(Color.black, 5));
								}
							}
							
							if (yPos + 1 != 8) {
								if (!board.getPiece(strToSquares[x][yPos + 1]).name().equalsIgnoreCase("none")) {
									squares[x][yPos + 1].setBackground(movableSquareColor);
									squares[x][yPos + 1].setBorder(BorderFactory.createLineBorder(Color.black, 5));
								}
							}
						}
					}
				}
			}
		});
	}
	
	
	private void castlingMovement(int i, int j) {
		Board board = boardPanel.getBoard();
		if (board.getContext().isCastleMove(new Move(strToSquares[i][j], strToSquares[xPos][yPos]))) {
			int x = 0;
			if (board.getSideToMove().flip().equals(Side.WHITE)) {
				x = 7;
			}
			if (board.getContext().isKingSideCastle(new Move(strToSquares[i][j],
					strToSquares[xPos][yPos])) && board.getSideToMove().flip().equals(Side.WHITE)) {
				reloadSquare(x, 5);
				reloadSquare(x, 7);
			} else if (board.getSideToMove().flip().equals(Side.WHITE)) {
				reloadSquare(x, 0);
				reloadSquare(x, 3);
			} else if (board.getSideToMove().flip().equals(Side.BLACK) && board.getContext().isKingSideCastle(new Move(strToSquares[i][j],
					strToSquares[xPos][yPos]))) {
				reloadSquare(x, 5);
				reloadSquare(x, 7);
			} else if (board.getSideToMove().flip().equals(Side.BLACK)) {
				reloadSquare(x, 0);
				reloadSquare(x, 3);
			}
		}
	}
	
	
	private void promotionMovement() {
		Board board = boardPanel.getBoard();
		Square moveToSquare = strToSquares[xPos][yPos];
		
		PieceType pieceType = board.getPiece(moveToSquare).getPieceType();
		if (pieceType == null) {
			return;
		}
		if (!board.getPiece(moveToSquare).getPieceType().equals(PieceType.PAWN)) {
			return;
		}
		if (!(xPos == 7) && !(xPos == 0)) {
			return;
		}
		if (board.getSideToMove().equals(Side.WHITE)) {
			board.setPiece(Piece.BLACK_QUEEN, moveToSquare);
		} else {
			board.setPiece(Piece.WHITE_QUEEN, moveToSquare);
		}
		reloadSquare(xPos, yPos);
	}
	
	
	private void reloadSquare(int i, int j) {
		BoardSquarePanel squarePanel = boardPanel.getSquares()[i][j];
		squarePanel.removeAll();
		String pieceName = boardPanel.getBoard().getPiece(strToSquares[i][j]).name().toLowerCase();
		if (boardPanel.getPiecesStringToImage().containsKey(pieceName)) {
			GUIPiece piece = new GUIPiece(new ImageIcon(boardPanel.getPiecesStringToImage().get(pieceName)));
			boardPanel.getPieces().add(piece);
			squarePanel.add(piece);
			squarePanel.revalidate();
			squarePanel.repaint();
		}
	}
	
	
	private void checkGameStates() {
		Board board = boardPanel.getBoard();
		
		board.isMated();
	}
	
	
	public boolean getMoving() {
		return isMoving;
	}
	
	
	public void setMoving(boolean moving) {
		isMoving = moving;
	}
}
