package me.Chanadu.Chess;

import javax.swing.*;

public class UndoPanel extends JPanel {
	
	JButton button = new JButton("Undo");
	
	public UndoPanel(ChessBoardPanel chessBoardPanel) {
		button.addActionListener(e -> {
			if (chessBoardPanel.getBoard().getHalfMoveCounter() >= 0) {
				//System.out.println(chessBoardPanel.getBoard().getMoveCounter());
				chessBoardPanel.getBoard().undoMove();
				System.out.println(chessBoardPanel.getBoard().toString());
				chessBoardPanel.removePieces();
				chessBoardPanel.reDrawBoard();
				chessBoardPanel.addPiecesToBoard();
			}
		});
		this.add(button);
	}
}
