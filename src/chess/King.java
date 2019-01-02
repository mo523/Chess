package chess;

@SuppressWarnings("serial")
public class King extends Piece {

	public King(boolean white, int row, int col) {
		super(white, row, col, new String[] { "      \u2584\u2584    ", "     \u2580\u2588\u2588\u2580   ",
				"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ", "     \u2590\u2588\u2588\u258C   ",
				"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ", "   \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580 " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		if (Math.abs(fromCol - toCol) > 1 || Math.abs(fromRow - toRow) > 1)
			return false;
		return true;
	}

	@Override
	public boolean noPieceInTheWay(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		if (CB[toRow][toCol] == null)
			return true;
		else if (CB[toRow][toCol].isWhite() != this.isWhite())
			return true;
		return false;
	}
}
