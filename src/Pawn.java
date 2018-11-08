
public class Pawn extends Piece
{
	private String icon[] = {
			"            ",
			"      \u2584\u2584    ",
			"     \u2580\u2588\u2588\u2580   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ",
			"    \u2580\u2580\u2580\u2580\u2580\u2580  ",
			"            ",
			};
	private int verticalMoveMax;
	private boolean firstMove = true;
	public Pawn( boolean white )
	{
		super(white);
		this.name = "Pawn";
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
	public boolean canPieceMoveLikeThat(int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB ) 
	{
		int yDiff = toRow - fromRow;
		int xDiff = Math.abs(toCol - fromCol);
		int tempY;
		
		if(xDiff > 1)
			return false;
		if(xDiff == 1 && CB[toRow][toCol] == null)
			return false;
		if((xDiff == 1 && CB[toRow][toCol].isWhite() == this.isWhite()))
			return false;
		if(xDiff == 0 && CB[toRow][toCol] != null)
			return false;
		
		tempY = verticalMoveMax * (firstMove ? 2 : 1);

		if(yDiff == tempY || yDiff == verticalMoveMax) {
			firstMove = false;
			return true;
		}	
		return false;
		
	}

	@Override
	public boolean noPieceInTheWay(int fromCol,
			int fromRow, int toCol, int toRow,
			Piece[][] CB) {
		//this is taken care of by canPieceMoveLikeThat
		return true;
	}

}
