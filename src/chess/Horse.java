package chess;

@SuppressWarnings("serial")
public class Horse extends Piece {
	public Horse(boolean white, int row, int col) {
		super(white, row, col,
				new String[] { "            ", "     \u2584\u2588\u2588\u2584   ",
						"    \u2588\u2588\u2588\u2584\u2588\u2588  ", "    \u2580\u2588\u2588\u2588\u2584   ",
						"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "            " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		Queen queen = new Queen(isWhite(), -1, -1);
		if (!queen.canPieceMoveLikeThat(fromRow, fromCol, toRow, toCol, CB) && Math.abs(fromCol - toCol) < 3
				&& Math.abs(fromRow - toRow) < 3)
			return true;
		else
			return false;
	}

	@Override
	public boolean noPieceInTheWay(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		return true; // because horses jump
	}
}
