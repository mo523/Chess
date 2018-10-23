
public class Horse extends Piece {
	private String icon[] = {
			"   \u2584\u263A\u2588\u2584\u2584  ",
			"  \u2588\u2588\u2588\u2580\u2580\u2588\u2588 ",
			"  \u2580\u2588\u2588\u2588\u2584   ",
			"  \u2584\u2588\u2588\u2588\u2588\u2584  "
			};
	private boolean white;
	
	public Horse(boolean white) {
		super( white);
		this.name = "Horse";
		displayCharacter = 'h';
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
	public boolean canPieceMoveLikeThat(int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate)
	{
		Queen queen = new Queen(white);
		if (!queen.canPieceMoveLikeThat(from_Y_Coordinate, from_X_Coordinate, to_Y_Coordinate, to_X_Coordinate)&&
				Math.abs(from_Y_Coordinate - to_Y_Coordinate) < 3 && Math.abs(from_X_Coordinate - to_X_Coordinate) < 3 )
							return true;
		else
							return false;
	}
}
