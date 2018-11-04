public class Bishop extends Piece {
	private String icon[] = {
			"          ",
			"    \u2588\u2588    ",
			"  \u2580\u2580\u2588\u2588\u2580\u2580  ",
			"   \u2584\u2588\u2588\u2584   ",
			"  \u2588\u2588\u2588\u2588\u2588\u2588  ",
			"        "
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
	public boolean canPieceMoveLikeThat(int from_X_Coordinate,int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ) {
		int yDiff = Math.abs(to_Y_Coordinate - from_Y_Coordinate);
		int xDiff = Math.abs(to_X_Coordinate - from_X_Coordinate);
		if(yDiff == xDiff)
			return true;
		return false;
	}
	@Override
	public boolean pieceInTheWay(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate,
			Piece[][] CB) {
		int  XMoveDistance =(from_X_Coordinate-to_X_Coordinate);
		int  YMoveDistance =(from_Y_Coordinate-to_Y_Coordinate);
		
		do
		{
			if(CB[from_Y_Coordinate-to_Y_Coordinate+YMoveDistance][from_X_Coordinate-to_X_Coordinate+XMoveDistance] != null)
				return true;
			
			else 
			{
				if (XMoveDistance>0)
					XMoveDistance--;
				
				if (XMoveDistance<0)
					XMoveDistance++;
				
				if (YMoveDistance>0)
					YMoveDistance--;
				
				if (YMoveDistance<0)
					YMoveDistance++;
					
					
			}
		}
		while (XMoveDistance!=0);
		
		return false;
		
		
		
	}
}
