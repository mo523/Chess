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
	public boolean noPieceInTheWay(int toRow, int toCol, Piece[][] CB) {
		Piece piece = CB[toRow][toCol];
		if (piece == null || piece.isWhite() != this.isWhite())
			return true;
		return false;
	}

	// CheckMate methods overriding Piece
	public boolean checkmate(ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard) {
		for (int toRow = this.getRow() - 1; toRow < this.getRow() + 1; toRow++)
			for (int toCol = this.getCol() - 1; toCol < this.getRow() + 1; toCol++) {
				if (toRow > 8 || toCol > 8)
					break;
				if (toRow == -1)
					toRow++;
				if (toCol == -1)
					toCol++;
				if (this.isLegalMove(toRow, toCol, pieces, chessBoard, this)) {
					int oldRow = this.getRow();
					int oldCol = this.getCol();
					Piece currPiece = this;
					Piece killPiece = chessBoard[toRow][toCol];
					currPiece.setRowCol(toRow, toCol);
					pieces.get(this.isWhite() ? 1 : 0).remove(killPiece);
					chessBoard[toRow][toCol] = currPiece;
					chessBoard[oldRow][oldCol] = null;
					boolean safe = false;
					if (!this.inCheck(this, pieces, chessBoard))
						safe = true;
					pieces.get(this.isWhite() ? 1 : 0).add(killPiece);
					chessBoard[toRow][toCol] = killPiece;
					chessBoard[oldRow][oldCol] = this;
					this.setRowCol(oldRow, oldCol);
					if (safe)
						return false;
				}
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
