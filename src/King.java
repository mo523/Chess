
public class King extends Piece {
	private String icon[] = {"     king     ", "              ", "              ", "              "};
	
	public King( boolean white) {
		super( white);
		this.name = "King";
		displayCharacter = 'k';
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
	public boolean canPieceMoveLikeThat(int from_Y_Coordinate,int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate) {
		if(Math.abs(from_Y_Coordinate - to_Y_Coordinate) > 1 || Math.abs(from_X_Coordinate - to_X_Coordinate) > 1)
			return false;
		return true;
	}
	

}
