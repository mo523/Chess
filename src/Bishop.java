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
		this.icon = new String[] {
				"          ",
				"    \u2588\u2588    ",
				"  \u2580\u2580\u2588\u2588\u2580\u2580  ",
				"   \u2584\u2588\u2588\u2584   ",
				"  \u2588\u2588\u2588\u2588\u2588\u2588  ",
				"        "
				};
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
	public boolean canPieceMoveLikeThat(int fromCol,int fromRow, int toCol, int toRow, Piece[][] CB ) {
		int yDiff = Math.abs(toRow - fromRow);
		int xDiff = Math.abs(toCol - fromCol);
		if(yDiff == xDiff)
			return true;
		return false;
	}

	@Override
	public boolean noPieceInTheWay(int fromCol,
			int fromRow, int toCol, int toRow,
			Piece[][] CB) {
		int  XMoveDistance =(fromCol-toCol);
		int  YMoveDistance =(fromRow-toRow);
		boolean done = false;
		do
		{
			
			if(CB[fromRow-YMoveDistance][fromCol-XMoveDistance] != null && 
					!(fromRow-YMoveDistance == toRow && fromCol- XMoveDistance == toCol))
				return false;
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
			if(XMoveDistance == 1 || XMoveDistance == -1 || XMoveDistance == 0)
				done = true;
		}
		while (!done);
		
		return true;
	}
}
/*	//@Override
	public boolean pieceInTheWay(int fromCol, int fromRow, int toCol, int toRow,
			Piece[][] CB) {
		int  XMoveDistance =(fromCol-toCol);
		int  YMoveDistance =(fromRow-toRow);
		
		do
		{
			if(CB[fromRow-YMoveDistance][fromCol-XMoveDistance] != null)
				return false;
			
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
		
		return true;
		
		
		
	}*/
