
public class Rook extends Piece {
	private String icon[] = {
			"          ",
			" \u258C \u2588  \u2588 \u2590 " ,
			" \u2580\u2588\u2588\u2588\u2588\u2588\u2588\u2580 ",
			"  \u2588\u2588\u2588\u2588\u2588\u2588  ",
			" \u2584\u2588\u2588\u2588\u2588\u2588\u2588\u2584 ",
			"        "
			};
	public Rook(boolean white) {
		super(white);
		this.name = "Rook";
		displayCharacter = 'r';
	}
	public String getIcon(int row)
	{
		return  "  " + icon[row] + "  ";
	}
	public boolean isWhite()
	{
		return white;
	}

	@Override
	public boolean canPieceMoveLikeThat(int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate, Piece[][] CB ) {
		int xDiff = Math.abs(to_X_Coordinate - from_X_Coordinate);
		int yDiff = Math.abs(to_Y_Coordinate - from_Y_Coordinate);
		if(xDiff > 0 && yDiff > 0)
			return false;
		return true;
	}
 
}
