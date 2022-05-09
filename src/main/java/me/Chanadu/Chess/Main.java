package me.Chanadu.Chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class Main {

    public static void main(String[] args) {
        //System.out.println("Hello World");
        
        Board board = new Board();
        board.doMove("e4");
        board.doMove("e5");
        board.doMove("Nf3");
        board.doMove("Nc6");
        board.doMove("Nc3");
        board.doMove("Nf6");
        
        System.out.println(board);
        new ChessGUI(board);
    }


}
