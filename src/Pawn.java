
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
	private boolean firstMove;
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
	public boolean canPieceMoveLikeThat(int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate) 
	{
		return true;
	}

}
