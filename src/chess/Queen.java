package chess;

@SuppressWarnings("serial")
public class Queen extends Piece {
	public Queen(boolean white, int row, int col) {
		super(white, row, col, new String[] { "            ", "     \u2584\u2590\u258C\u2584   ",
				"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "     \u2590\u2588\u2588\u258C   ",
				"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "   \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580 " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB) {
		Rook rook = new Rook(isWhite(), this.getRow(), this.getCol());
		Bishop bishop = new Bishop(isWhite(), this.getRow(), this.getCol());
		return (rook.canPieceMoveLikeThat( toRow, toCol, CB)
				|| bishop.canPieceMoveLikeThat( toRow, toCol, CB));
	}

	@Override
	public boolean noPieceInTheWay(int toRow, int toCol, Piece[][] CB) {

		Rook rook = new Rook(isWhite(), -1, -1);
		Bishop bishop = new Bishop(isWhite(), -1, -1);

		int yDiff = Math.abs(toRow - this.getRow());
		int xDiff = Math.abs(toCol - this.getCol());
		if (yDiff == xDiff)
			return bishop.noPieceInTheWay(toRow, toCol, CB);
		else
			return rook.noPieceInTheWay(toRow, toCol, CB);
	}
}
