
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
	private int verticalMoveMax;
	private boolean firstMove = true;
	public Pawn( boolean white )
	{
		super(white);
		verticalMoveMax = white ? 1 : -1;
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
	public boolean canPieceMoveLikeThat(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ) 
	{
		int yDiff = to_Y_Coordinate - from_Y_Coordinate;
		int xDiff = Math.abs(to_X_Coordinate - from_X_Coordinate);
		boolean goodSoFar = true;
		int tempY;
		
		if(xDiff > 1)
			return false;
		if(xDiff == 1 && CB[to_X_Coordinate][to_Y_Coordinate] == null)
			return false;
		if((xDiff == 1 && CB[to_X_Coordinate][to_Y_Coordinate].isWhite() == this.isWhite()))
			return false;
		
		if(firstMove)
			tempY = verticalMoveMax * 2;
		else
			tempY = verticalMoveMax;
			
		if(yDiff == tempY || yDiff == verticalMoveMax) {
			firstMove = false;
			return true;
		}	
		else 
			return false;
		
	}

}
