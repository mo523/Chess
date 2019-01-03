package chess;

@SuppressWarnings("serial")
public class Rook extends Piece {
	public Rook(boolean white, int row, int col) {
		super(white, row, col,
				new String[] { "            ", "    \u258C\u2588\u2590\u258C\u2588\u2590  ",
						"    \u2580\u2588\u2588\u2588\u2588\u2580  ", "     \u2588\u2588\u2588\u2588   ",
						"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "            " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB) {
		int yDiff = Math.abs(toRow - this.getRow());
		int xDiff = Math.abs(toCol - this.getCol());
		if (yDiff > 0 && xDiff > 0)
			return false;
		return true;
	}

	@Override
	public boolean pieceInTheWay(int toRow, int toCol, Piece[][] CB) {
		int fromRow = this.getRow();
		int fromCol = this.getCol();
		int yDiff = toRow - fromRow;
		int xDiff = toCol - fromCol;
//		int yDirection = yDiff == 0 ? 0 : yDiff > 0 ? 1 : -1;
//		int xDirection = xDiff == 0 ? 0 : xDiff > 0 ? 1 : -1;
//		int distance = Math.abs(yDiff + xDiff);
//		do
//		{
//			if (CB[this.getRow() - xDirection][this.getCol() - yDirection] != null && )
//			distance--;
//		}	while (distance != 0); 
		
		if (xDiff == 0) {
			if (yDiff > 0) {
				for (int i = fromRow + 1; yDiff != 1; i++) {
					yDiff--;
					if (CB[i][fromCol] != null)
						return true;
				}
			} else {
				for (int i = fromRow - 1; yDiff != -1; i--) {
					yDiff++;
					if (CB[i][fromCol] != null)
						return true;
				}
			}
		} else {
			if (xDiff > 0) {
				for (int i = fromCol + 1; xDiff != 1; i++) {
					xDiff--;
					if (CB[fromRow][i] != null)
						return true;
				}
			} else {
				for (int i = fromCol - 1; xDiff != -1; i--) {
					xDiff++;
					if (CB[fromRow][i] != null)
						return true;
				}
			}
		}
		return false;
	}
}
