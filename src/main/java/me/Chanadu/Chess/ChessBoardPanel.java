package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ChessBoardPanel extends JPanel {
	
	
	JFrame mainFrame;
	
	Color backgroundColor = new Color(0, 0, 0);
	Color lightColor = new Color(240, 217, 181);
	Color darkColor = new Color(181, 136, 99);
	
	JPanel[][] squares = new JPanel[8][8];
	int squareSize = 85;
	Image chessPiecesPNG;
	Board board;
	
	HashMap<String, Image> piecesStringToImage = new HashMap<>();
	ArrayList<GUIPiece> pieces = new ArrayList<>();
	
	
	ChessBoardPanel(JFrame mainFrame) {
		board = new Board();
		board.doMove(new Move(Square.E2, Square.E4));
		board.doMove(new Move(Square.D7, Square.D5));
		board.doMove(new Move(Square.E4, Square.D5));
		this.mainFrame = mainFrame;
		
		setPanel();
		createBoard();
		
		loadChessPiecesPNG();
		splitChessPiecesPNG();
		
		addPiecesToBoard();
	}
	
	
	//Copied From Stack Overflow
	// https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
	private BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		
		// Create a buffered image with transparency
		BufferedImage b = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		// Draw the image on to the buffered image
		Graphics2D bGr = b.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		
		// Return the buffered image
		return b;
	}
	
	
	private void loadChessPiecesPNG() {
		try {
			URL url = Objects.requireNonNull(this.getClass().getResource("/Chess Pieces.png"));
			chessPiecesPNG = new ImageIcon(url).getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void splitChessPiecesPNG() {
		BufferedImage BIChessPieces = toBufferedImage(chessPiecesPNG);
		
		piecesStringToImage.put("white_pawn", BIChessPieces.getSubimage(0, 0, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("white_knight", BIChessPieces.getSubimage(16, 0, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("white_bishop", BIChessPieces.getSubimage(32, 0, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("white_rook", BIChessPieces.getSubimage(48, 0, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("white_king", BIChessPieces.getSubimage(64, 0, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("white_queen", BIChessPieces.getSubimage(80, 0, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("black_pawn", BIChessPieces.getSubimage(0, 16, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("black_knight", BIChessPieces.getSubimage(16, 16, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("black_bishop", BIChessPieces.getSubimage(32, 16, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("black_rook", BIChessPieces.getSubimage(48, 16, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("black_king", BIChessPieces.getSubimage(64, 16, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
		piecesStringToImage.put("black_queen", BIChessPieces.getSubimage(80, 16, 16, 16).getScaledInstance(80, 80,
				Image.SCALE_SMOOTH));
	}
	
	
	private void setPanel() {
		this.setBackground(backgroundColor);
		this.setLayout(new GridLayout(8, 8, 0, 0));
		this.setPreferredSize(new Dimension(8 * squareSize, 8 * squareSize));
	}
	
	
	private void createBoard() {
		Color color;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				JPanel currentSquare;
				currentSquare = new JPanel();
				if ((i + j) % 2 == 0) {
					color = lightColor;
				} else {
					color = darkColor;
				}
				currentSquare.setBackground(color);
				currentSquare.setPreferredSize(new Dimension(squareSize, squareSize));
				int finalI = i;
				int finalJ = j;
				currentSquare.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						
						if (currentSquare.getBackground().equals(new Color(59, 108, 73))) {
							
							for (GUIPiece piece : pieces) {
								if (piece.isMoving) {
									
									currentSquare.add(piece);
									board.doMove(new Move(piece.strToSquares[piece.rowNum][piece.colNum], piece.strToSquares[finalI][finalJ]));
									piece.isMoving = false;
									
									piece.reDrawBoard();
									removePieces();
									addPiecesToBoard();
									
									break;
								}
							}
						}
					}
				});
				squares[i][j] = currentSquare;
				this.add(squares[i][j]);
			}
		}
	}
	
	
	public void addPiecesToBoard() {
		int rowNum = 7;
		int colNum = 0;
		for (int i = 0; i < board.boardToArray().length - 1; i++) {
			
			String pieceName = board.boardToArray()[i].toString().toLowerCase();
			
			if (piecesStringToImage.containsKey(pieceName)) {
				GUIPiece piece = new GUIPiece(pieceName, new ImageIcon(piecesStringToImage.get(pieceName)), this, rowNum, colNum);
				
				pieces.add(piece);
				
				squares[rowNum][colNum].add(piece);
			} else if (!pieceName.equals("none")) {
				System.out.println(pieceName);
				System.out.println(piecesStringToImage.get(pieceName));
			}
			
			colNum++;
			if (colNum >= 8) {
				
				rowNum--;
				colNum = 0;
			}
		}
	}
	
	
	public void removePieces() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j].removeAll();
				squares[i][j].revalidate();
				squares[i][j].repaint();
			}
		}
	}
	
	
	public JPanel[][] getSquares() {
		return squares;
	}
	
	
	public ArrayList<GUIPiece> getPieces() {
		return pieces;
	}
	
	
	public Board getBoard() {
		return board;
	}
}
