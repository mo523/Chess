
public class Queen extends Piece {
	private String icon[] = {"     queen    ", "              ", "              ", "              "};
	private boolean white;
	public Queen( boolean white) {
		super(white);
		this.name = "Queen";
		displayCharacter = 'q';
		this.white=white;
	}
	public String getIcon(int row)
	{
		return icon[row];
	}
	public boolean isWhite()
	{
		return white;
	}
	
	public boolean canPieceMoveLikeThat(int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate) 
	{
		Rook rook = new Rook(white);
		King king = new King(white);
		Bishop bishop = new Bishop(white);
		
		return (rook.canPieceMoveLikeThat(from_Y_Coordinate, from_X_Coordinate, to_Y_Coordinate, to_X_Coordinate)||
				king.canPieceMoveLikeThat(from_Y_Coordinate, from_X_Coordinate, to_Y_Coordinate, to_X_Coordinate)||
				bishop.canPieceMoveLikeThat(from_Y_Coordinate, from_X_Coordinate, to_Y_Coordinate, to_X_Coordinate));
	}
}
