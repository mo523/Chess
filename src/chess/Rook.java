package chess;

@SuppressWarnings("serial")
public class Rook extends Piece
{
	public Rook(boolean white, int row, int col)
	{
		super(white, row, col,
				new String[] { "            ", "    \u258C\u2588\u2590\u258C\u2588\u2590  ",
						"    \u2580\u2588\u2588\u2588\u2588\u2580  ", "     \u2588\u2588\u2588\u2588   ",
						"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "            " });
	}

	@Override
	public boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] CB)
	{
		int yDiff = Math.abs(toRow - this.getRow());
		int xDiff = Math.abs(toCol - this.getCol());
		if (yDiff > 0 && xDiff > 0)
			return false;
		return true;
	}

	@Override
	public boolean pieceInTheWay(int toRow, int toCol, Piece[][] CB)
	{
		int fromRow = this.getRow();
		int fromCol = this.getCol();
		int rowDiff = toRow - fromRow;
		int colDiff = toCol - fromCol;
		int colDirection = colDiff == 0 ? 0 : colDiff > 0 ? 1 : -1;
		int rowDirection = rowDiff == 0 ? 0 : rowDiff > 0 ? 1 : -1;
		int distance = Math.abs(rowDiff + colDiff) - 1;
		while (distance != 0)
		{
			if (CB[fromRow + distance * rowDirection][fromCol + distance * colDirection] != null)
				return true;
			distance--;
		}
		return false;
	}

	@Override
	public int getAIValue()
	{
		return 550;
	}
}
