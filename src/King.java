
public class King extends Piece {
	private String icon[] = {
			"      \u2584\u2584      ",
			"     \u2580\u2588\u2588\u2580     ",
			"    \u25A0\u2588\u2588\u2588\u2588\u25A0    ",
			"     \u2590\u2588\u2588\u258C     ",
			"    \u25A0\u2588\u2588\u2588\u2588\u25A0    ",
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
	public boolean canPieceMoveLikeThat(int from_X_Coordinate,int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ) {
		if(Math.abs(from_X_Coordinate - to_X_Coordinate) > 1 || Math.abs(from_Y_Coordinate - to_Y_Coordinate) > 1)
			return false;
		return true;
	}

	@Override
	public boolean noPieceInTheWay(int from_X_Coordinate,
			int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate,
			Piece[][] CB) {
		if(CB[to_Y_Coordinate][to_X_Coordinate] == null)
			return true;
		else if(CB[to_Y_Coordinate][to_X_Coordinate].isWhite() != this.isWhite())
			return true;
		return false;
	}
	

}
