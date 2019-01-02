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
	public boolean canPieceMoveLikeThat(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		int yDiff = Math.abs(toRow - fromRow);
		int xDiff = Math.abs(toCol - fromCol);
		if (yDiff == xDiff)
			return true;
		return false;
	}

	@Override
	public boolean noPieceInTheWay(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		int XMoveDistance = (fromCol - toCol);
		int YMoveDistance = (fromRow - toRow);
		boolean done = false;
		do {
			if (CB[fromRow - YMoveDistance][fromCol - XMoveDistance] != null
					&& !(fromRow - YMoveDistance == toRow && fromCol - XMoveDistance == toCol))
				return false;
			else {
				if (XMoveDistance > 0)
					XMoveDistance--;

				if (XMoveDistance < 0)
					XMoveDistance++;

				if (YMoveDistance > 0)
					YMoveDistance--;

				if (YMoveDistance < 0)
					YMoveDistance++;
			}
			if (XMoveDistance == 0)
				done = true;
		} while (!done);
		return true;
	}

}
