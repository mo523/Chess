package chess;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class King extends Piece {

	public King(boolean white, int row, int col) {
		super(white, row, col, new String[] { "      \u2584\u2584    ", "     \u2580\u2588\u2588\u2580   ",
				"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ", "     \u2590\u2588\u2588\u258C   ",
				"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ", "   \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580 " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB) {
		if (Math.abs(this.getCol() - toCol) > 1 || Math.abs(this.getRow() - toRow) > 1)
			return false;
		return true;
	}

	@Override
	public boolean pieceInTheWay(int toRow, int toCol, Piece[][] CB) {
		Piece piece = CB[toRow][toCol];
		if (piece == null || piece.isWhite() != this.isWhite())
			return false;
		return true;
	}

	// CheckMate methods overriding Piece
	public boolean checkmate(ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard) {
		for (int toRow = this.getRow() - 1; toRow < this.getRow() + 1; toRow++)
			for (int toCol = this.getCol() - 1; toCol < this.getRow() + 1; toCol++) {
				if (toRow > 8 || toCol > 8)
					break;
				if (toRow == -1 || toCol == -1)
					continue;
				if (this.isLegalMove(toRow, toCol, pieces, chessBoard, this))
					return false;
			}
		return true;
	}

	public boolean inCheck(Piece king, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard) {
		for (Piece piece : pieces.get(king.isWhite() ? 1 : 0))
			if (piece.isLegalMove(this.getRow(), this.getCol(), pieces, chessBoard, this))
				return true;// can kill
		return false;
	}

}
