
public class King extends Piece {
	private String icon[] = {
			"      \u2584\u2584    ",
			"     \u2580\u2588\u2588\u2580   ",
			"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ",
			"     \u2590\u2588\u2588\u258C   ",
			"    \u25A0\u2588\u2588\u2588\u2588\u25A0  ",
			"   \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580 "};
	
	public King( boolean white) {
		super( white);
		this.name = "King";
	}

	public String getIcon(int row)
	{
		return icon[row];
	}
	public boolean isWhite()
	{
		return white;
	}

	@Override
	public boolean canPieceMoveLikeThat(int fromCol,int fromRow, int toCol, int toRow, Piece[][] CB ) {
		if(Math.abs(fromCol - toCol) > 1 || Math.abs(fromRow - toRow) > 1)
			return false;
		return true;
	}

	@Override
	public boolean noPieceInTheWay(int fromCol,
			int fromRow, int toCol, int toRow,
			Piece[][] CB) {
		if(CB[toRow][toCol] == null)
			return true;
		else if(CB[toRow][toCol].isWhite() != this.isWhite())
			return true;
		return false;
	}
	

}
