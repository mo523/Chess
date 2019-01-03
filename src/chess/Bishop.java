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
		//The absolute value of the x minus y distance should be 0
		if (Math.abs(toRow - toCol) == 0)
			return true;
		return false;
	}

	@Override
	public boolean noPieceInTheWay(int toRow, int toCol, Piece[][] CB) {
		int distance = (this.getCol() - toCol);
		int direction = distance > 0 ? 1 : -1;
		do {      
			if (CB[this.getRow() + direction][this.getCol() + direction] != null && this.getRow() - distance != toRow )
				return false;
			distance += direction;
		} while (distance != 0);
		return true;
	}

}
