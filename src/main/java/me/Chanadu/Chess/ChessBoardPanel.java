package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ChessBoardPanel extends JPanel {
	
	
	JFrame mainFrame;
	
	private final Color backgroundColor = new Color(0, 0, 0);
	private final Color lightColor = new Color(240, 217, 181);
	private final Color darkColor = new Color(181, 136, 99);
	
	BoardSquarePanel[][] squares = new BoardSquarePanel[8][8];
	Image chessPiecesPNG;
	Board board;
	
	HashMap<String, Image> piecesStringToImage = new HashMap<>();
	ArrayList<GUIPiece> pieces = new ArrayList<>();
	
	
	ChessBoardPanel(JFrame mainFrame) {
		board = new Board();
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
	}
	
	
	private void createBoard() {
		Color color;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					color = lightColor;
				} else {
					color = darkColor;
				}
				
				BoardSquarePanel square = new BoardSquarePanel(i, j, color, this);
				
				squares[i][j] = square;
				this.add(squares[i][j]);
			}
		}
	}
	
	
	public void reDrawBoard() {
		Color color;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					color = lightColor;
				} else {
					color = darkColor;
				}
				
				squares[i][j].setBackground(color);
				squares[i][j].setBorder(new EmptyBorder(0, 0, 0, 0));
				squares[i][j].setMoving(false);
				
			}
		}
		
		
	}
	
	
	public void addPiecesToBoard() {
		int rowNum = 7;
		int colNum = 0;
		for (int i = 0; i < board.boardToArray().length - 1; i++) {
			
			String pieceName = board.boardToArray()[i].toString().toLowerCase();
			
			if (piecesStringToImage.containsKey(pieceName)) {
				GUIPiece piece = new GUIPiece( new ImageIcon(piecesStringToImage.get(pieceName)));
				pieces.add(piece);
				squares[rowNum][colNum].add(piece);
				squares[rowNum][colNum].revalidate();
				squares[rowNum][colNum].repaint();
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
			}
		}
	}
	
	
	public BoardSquarePanel[][] getSquares() {
		return squares;
	}
	
	
	public Board getBoard() {
		return board;
	}
	
	
	public HashMap<String, Image> getPiecesStringToImage() {
		return piecesStringToImage;
	}

	
}
