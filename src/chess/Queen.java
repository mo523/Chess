package chess;

@SuppressWarnings("serial")
public class Queen extends Piece {
	private String icon[] = { "            ", "     \u2584\u2590\u258C\u2584   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "     \u2590\u2588\u2588\u258C   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "   \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580 " };
	private final boolean enPassantAble = false;

	public Queen(boolean white, int row, int col) {
		super(white, row, col);
		super.icon = icon;
	}

	@Override
	public boolean canPieceMoveLikeThat(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		Rook rook = new Rook(white, -1, 1 - 1);
		Bishop bishop = new Bishop(white, -1, -1);
		return (rook.canPieceMoveLikeThat(fromRow, fromCol, toRow, toCol, CB)
				|| bishop.canPieceMoveLikeThat(fromRow, fromCol, toRow, toCol, CB));
	}

	@Override
	public boolean noPieceInTheWay(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {

		Rook rook = new Rook(white, -1, -1);
		Bishop bishop = new Bishop(white, -1, -1);

		int yDiff = Math.abs(toRow - fromRow);
		int xDiff = Math.abs(toCol - fromCol);
		if (yDiff == xDiff)
			return bishop.noPieceInTheWay(fromRow, fromCol, toRow, toCol, CB);
		else
			return rook.noPieceInTheWay(fromRow, fromCol, toRow, toCol, CB);
	}

	public boolean isEnPassantAble() {
		return enPassantAble;
	}
}
