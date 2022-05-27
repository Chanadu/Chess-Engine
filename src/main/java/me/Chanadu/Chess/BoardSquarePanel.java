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
	
	Color movableSquareColor = new Color(59, 108, 73);
	Color movingSquareColor = new Color(149, 207, 167);
	
	Square[][] strToSquares;
	
	public BoardSquarePanel(int x, int y, Color color, ChessBoardPanel boardPanel) {
		this.boardPanel = boardPanel;
		xPos = x;
		yPos = y;
		startingColor = color;
		strToSquares = boardPanel.getStrToSquares();
		
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
								boardPanel.reloadSquare(i, j);
								boardPanel.reloadSquare(xPos, yPos);
								
								//En-Passant
								if (pieceName.contains("pawn")) {
									if (j != 0) {
										boardPanel.reloadSquare(i, j - 1);
									}
									if (j != 7) {
										boardPanel.reloadSquare(i, j + 1);
									}
								}
								
								//Check for En-Passant in the Board
								if (board.getPiece(strToSquares[xPos][yPos]) != null) {
									if (board.getPiece(strToSquares[xPos][yPos]).name().contains("pawn")) {
										if (board.getPiece(strToSquares[xPos][yPos]).getPieceSide().equals(Side.BLACK)) {
											if (xPos == 2) {
												boardPanel.reloadSquare(xPos, yPos + 1);
											}
										} else {
											if (xPos == 5) {
												boardPanel.reloadSquare(xPos, yPos - 1);
											}
										}
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
		
		//Set the Title of the titlePanel if the board is mated or stalemate
		if (board.isMated()) {
			if (board.getSideToMove().equals(Side.WHITE)) {
				boardPanel.getTitlePanel().setTitle("Black Wins!");
			} else {
				boardPanel.getTitlePanel().setTitle("White Wins!");
			}
		} else if (board.isStaleMate()) {
			boardPanel.getTitlePanel().setTitle("Stalemate!");
		}
		
		
	}
	
	
	public boolean getMoving() {
		return isMoving;
	}
	
	
	public void setMoving(boolean moving) {
		isMoving = moving;
	}
}
