package me.Chanadu.Chess;

import javax.swing.*;
import java.util.function.Function;

public class UndoPanel extends JPanel {
	
	JButton button = new JButton("Undo");
	
	public UndoPanel(ChessBoardPanel chessBoardPanel) {
		button.addActionListener(e -> {
			if (chessBoardPanel.getBoard().getHalfMoveCounter() >= 0) {
				chessBoardPanel.getBoard().undoMove();
				chessBoardPanel.removePieces();
				chessBoardPanel.reDrawBoard();
				chessBoardPanel.addPiecesToBoard();
			}
		});
		this.add(button);
	}
}
