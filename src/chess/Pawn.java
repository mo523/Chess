package chess;

@SuppressWarnings("serial")
public class Pawn extends Piece
{
	private int verticalMove;
	private boolean enPassantAble = false;

	public Pawn(boolean white, int row, int col)
	{
		super(white, row, col,
				new String[] { "            ", "      \u2584\u2584    ", "     \u2580\u2588\u2588\u2580   ",
						"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "    \u2580\u2580\u2580\u2580\u2580\u2580  ",
						"            ", });
		verticalMove = white ? 1 : -1;
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB)
	{
		int yDiff = toRow - this.getRow();
		int xDiff = toCol - this.getCol();
		int yDiffAbs = Math.abs(yDiff);
		int xDiffAbs = Math.abs(xDiff);
		boolean firstMove = this.getRow() == (this.isWhite() ? 1 : 6);

		// If horizontal distance is > 1
		// If if vertical distance is > 2
		// If vertical direction is wrong
		// xDiffAbs >  yDiffAbs
		if (xDiffAbs > 1 || yDiffAbs > 2 || yDiff / verticalMove < 0 || (yDiffAbs > 1 && !firstMove))
			return false;

		// If moving forward one and nothing in the way
		if (xDiffAbs == 0 && yDiffAbs == 1 && CB[toRow][toCol] == null)
			return true;

		// If moving forward 2 and first move and nothing in the way
		if (xDiffAbs == 0 && yDiffAbs == 2 && firstMove && CB[toRow][toCol] == null
				&& CB[toRow + verticalMove][toCol] == null)
			return true;

		// If killing by one diagonal
		if (xDiffAbs == yDiffAbs && CB[toRow][toCol] != null && CB[toRow][toCol].isWhite() != this.isWhite())
			return true;

		// If enPassanting
		if ((toRow == 5 || toRow == 2) && CB[toRow - verticalMove][toCol] instanceof Pawn)
		{
			Pawn enps = (Pawn) CB[toRow - verticalMove][toCol];
			if (enps.enPassantAble())
			{
				CB[toRow][toCol] = enps;
				CB[enps.getRow()][enps.getCol()] = null;
				enps.setRowCol(enps.getRow() + verticalMove, enps.getCol());
				return true;
			}
		}

		// If moving left or right but not up
		// When it's not enPassantable
		return false;
	}

	@Override
	public boolean pieceInTheWay(int toRow, int toCol, Piece[][] CB)
	{
		// this is taken care of by canPieceMoveLikeThat
		return false;
	}

	public boolean enPassantAble()
	{
		return enPassantAble;
	}

	public void setEnPassant(boolean enp)
	{
		enPassantAble = enp;
	}

	@Override
	public int getAIValue()
	{
		return 100;
	}
}