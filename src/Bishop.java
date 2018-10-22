public class Bishop extends Piece {
	private String icon[] = {
			"    \u2588\u2588    ",
			"  \u2580\u2580\u2588\u2588\u2580\u2580  ",
			"   \u2584\u2588\u2588\u2584   ",
			"  \u2588\u2588\u2588\u2588\u2588\u2588  "
			};
	
	public Bishop( boolean white) {
		super(white);
		this.name = "Bishop";
		displayCharacter = 'b';
	}
	public String getIcon(int row)
	{
		return "  " + icon[row] + "  ";
	}
	public boolean isWhite()
	{
		return white;
	}
	@Override
	public boolean canPieceMoveLikeThat(int from_Y_Coordinate,int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate) {
		int xDiff = Math.abs(to_X_Coordinate - from_X_Coordinate);
		int yDiff = Math.abs(to_Y_Coordinate - from_Y_Coordinate);
		if(xDiff == yDiff)
			return true;
		return false;
	}
}
