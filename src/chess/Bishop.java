package chess;

@SuppressWarnings("serial")
public class Bishop extends Piece {
	public Bishop(boolean white, int row, int col) {
		super(white, row, col,
				new String[] { "            ", "     \u2584\u2588\u2580\u2584   ",
						"    \u2580\u2588\u2584\u2588\u2588\u2580  ", "      \u2588\u2588    ",
						"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "            " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB) {
		// Check if the absolute move distance
		if (Math.abs(this.getRow() - toRow) == Math.abs(this.getCol() - toCol))
			return true;
		return false;
	}

	@Override
	public boolean pieceInTheWay(int toRow, int toCol, Piece[][] CB) {
		// Get the total distance the piece would like to go
		int fromCol = this.getCol();
		int fromRow = this.getRow();
		int distance = Math.abs(fromCol - toCol) - 1;
		// Get the direction the piece would like to go
		// for row up is positive and down is negative
		// for column right is positive and left is negative
		int rowDirection = toRow - fromRow > 0 ? 1 : -1;
		int colDirection = toCol - fromCol > 0 ? 1 : -1;
		do {
			if (CB[fromRow + distance * rowDirection][fromCol + distance * colDirection] != null)
				return true;
			distance--;
		} while (distance != 0);
		return false;
	}

}
