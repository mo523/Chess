
public class Horse extends Piece {
	private String icon[] = {
			"    \u2584\u2584    ",
			"  \u2584\u2588\u2588\u2588\u2588\u2584  ",
			" \u2588\u2588\u2588\u2588\u2588\u2584\u2588\u2588 ",
			" \u2580\u2588\u2588\u2588\u2588\u2584   ",
			" \u2584\u2584\u2588\u2588\u2588\u2588\u2588\u2584 ",
			" \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580"
			};	
	public Horse(boolean white) {
		super( white);
		this.name = "Horse";
		displayCharacter = 'h';
	}
	public String getIcon(int row)
	{
		return "  " + icon[row] + (row == 5 ? " " :"  ");
	}
	public boolean isWhite()
	{
		return white;
	}
	
	@Override
	public boolean canPieceMoveLikeThat(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB )
	{
		Queen queen = new Queen(white);
		if (!queen.canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)&&
				Math.abs(from_X_Coordinate - to_X_Coordinate) < 3 && Math.abs(from_Y_Coordinate - to_Y_Coordinate) < 3 )
							return true;
		else
							return false;
	}
	//@Override
	public boolean pieceInTheWay(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate,
			Piece[][] CB) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean noPieceInTheWay(int from_X_Coordinate,
			int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate,
			Piece[][] CB) {
		
		return true;
	}
}
