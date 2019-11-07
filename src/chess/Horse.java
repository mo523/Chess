package chess;

@SuppressWarnings("serial")
public class Horse extends Piece
{
	public Horse(boolean white, int row, int col)
	{
		super(white, row, col,
				new String[] { "            ", "     \u2584\u2588\u2588\u2584   ",
						"    \u2588\u2588\u2588\u2584\u2588\u2588  ", "    \u2580\u2588\u2588\u2588\u2584   ",
						"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "            " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB)
	{
		//Horses can only move where queens cannot
		//and they are limited to a distance of 2
		Queen queen = new Queen(isWhite(), this.getRow(), this.getCol());
		if (!queen.canPieceMoveLikeThat(toRow, toCol, CB) && Math.abs(this.getCol() - toCol) < 3
				&& Math.abs(this.getRow() - toRow) < 3)
			return true;
		return false;
	}

	@Override
	public boolean pieceInTheWay(int toRow, int toCol, Piece[][] CB)
	{
		//Horses jump
		return false;
	}

	@Override
	public int getAIValue()
	{
		return 350;
	}
}
