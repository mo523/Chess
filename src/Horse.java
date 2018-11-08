
public class Horse extends Piece {
	private String icon[] = {
			"          ",
			"  \u2584\u2588\u2588\u2588\u2588\u2584  ",
			" \u2588\u2588\u2588\u2588\u2588\u2584\u2588\u2588 ",
			" \u2580\u2588\u2588\u2588\u2588\u2584   ",
			" \u2584\u2584\u2588\u2588\u2588\u2588\u2588\u2584 ",
			//" \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580"
			"         "
			};	
	public Horse(boolean white) {
		super( white);
		this.name = "Horse";
	}
	public String getIcon(int row)
	{
		return "  " + icon[row] + (row == 5 ? " " :"  ");
	}
	public boolean isWhite()
	{
		return white;
	}
	
	@Override
	public boolean canPieceMoveLikeThat(int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB )
	{
		Queen queen = new Queen(white);
		if (!queen.canPieceMoveLikeThat(fromCol, fromRow, toCol, toRow, CB)&&
				Math.abs(fromCol - toCol) < 3 && Math.abs(fromRow - toRow) < 3 )
							return true;
		else
							return false;
	}
	
	@Override
	public boolean noPieceInTheWay(int fromCol,
			int fromRow, int toCol, int toRow,
			Piece[][] CB) {
		return true; // because horses jump
	}
}
