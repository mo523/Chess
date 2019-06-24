package chess;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class King extends Piece
{
	public King(boolean white, int row, int col)
	{
		super(white, row, col, new String[] { "      \u2584\u2584    ", "     \u2580\u2588\u2588\u2580   ",
				"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ", "     \u2590\u2588\u2588\u258C   ",
				"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ", "   \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580 " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB)
	{
		if (Math.abs(this.getCol() - toCol) > 1 || Math.abs(this.getRow() - toRow) > 1)
			return false;
		return true;
	}

	@Override
	public boolean pieceInTheWay(int toRow, int toCol, Piece[][] CB)
	{
		Piece piece = CB[toRow][toCol];
		if (piece == null || piece.isWhite() != this.isWhite())
			return false;
		return true;
	}

	// CheckMate methods overriding Piece
	public boolean checkmate(ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard)
	{
		for (Piece piece : pieces.get(this.isWhite() ? 0 : 1))
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					if (piece.isLegalMove(i, j, pieces, chessBoard, this, true))
						return false;
		return true;
	}

	public boolean inCheck(ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard)
	{
		for (Piece piece : pieces.get(this.isWhite() ? 1 : 0))
			if (piece.isLegalCheck(this.getRow(), this.getCol(), pieces, chessBoard, this, true))
				return true;
		return false;
	}

	public int getAIValue()
	{
		return 10000;
	}
}
