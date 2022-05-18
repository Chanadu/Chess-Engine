package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class Main {
	
	public static void main(String[] args) {
		//System.out.println("Hello World");
		
		Board board = new Board();
		//System.out.println(board.isMoveLegal(new Move(Square.E4, Square.E8), true));
		
		System.out.println(board.legalMoves());
		new ChessGUI(board);
	}
	
	
}
