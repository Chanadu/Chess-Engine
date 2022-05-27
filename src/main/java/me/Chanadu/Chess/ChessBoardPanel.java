package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ChessBoardPanel extends JPanel {
	
	
	private final Color backgroundColor = new Color(0, 0, 0);
	private final Color lightColor = new Color(240, 217, 181);
	private final Color darkColor = new Color(181, 136, 99);
	JFrame mainFrame;
	TitlePanel titlePanel;
	BoardSquarePanel[][] squares = new BoardSquarePanel[8][8];
	Image chessPiecesPNG;
	Board board;
	
	HashMap<String, Image> piecesStringToImage = new HashMap<>();
	ArrayList<GUIPiece> pieces = new ArrayList<>();
	
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
	
	
	ChessBoardPanel(JFrame mainFrame, TitlePanel titlePanel) {
		this.titlePanel = titlePanel;
		
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
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				reloadSquare(i, j);
			}
		}
	}
	
	
	public void reloadSquare(int i, int j) {
		BoardSquarePanel squarePanel = squares[i][j];
		squarePanel.removeAll();
		String pieceName = board.getPiece(strToSquares[i][j]).name().toLowerCase();
		if (piecesStringToImage.containsKey(pieceName)) {
			GUIPiece piece = new GUIPiece(new ImageIcon(piecesStringToImage.get(pieceName)));
			pieces.add(piece);
			squarePanel.add(piece);
			squarePanel.revalidate();
			squarePanel.repaint();
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
	
	
	public ArrayList<GUIPiece> getPieces() {
		return pieces;
	}
	
	
	public TitlePanel getTitlePanel() {
		return titlePanel;
	}
	
	
	public Square[][] getStrToSquares() {
		return strToSquares;
	}
}
