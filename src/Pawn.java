
public class Pawn extends Piece
{
	private String icon[] = {
			"              ",
			"      \u2584\u2584      ",
			"     \u2580\u2588\u2588\u2580     ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584    ",
			"    \u2580\u2580\u2580\u2580\u2580\u2580    ",
			"            ",
			};
	private int verticalMoveAmount;
	private boolean firstMove = true;
	public Pawn( boolean white )
	{
		super(white);
		verticalMoveAmount = white ? 1 : -1;
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
	public boolean canPieceMoveLikeThat(int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate, Piece[][] CB ) 
	{
		int xDiff = Math.abs(to_X_Coordinate - from_X_Coordinate);
		int yDiff = Math.abs(to_Y_Coordinate - from_Y_Coordinate);
		if(xDiff > 1)
			return false;
		if(xDiff > 0 && CB[to_X_Coordinate][to_Y_Coordinate].isWhite() != this.isWhite())
			
			
			
		int tempVerticalMoveAmount = firstMove ? (2) : 1;
		if(yDiff )
		
		return true;
	}

}
