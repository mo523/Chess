
public class Queen extends Piece {
	private String icon[] = {
			"              ",
			"     queen    ",
			"              ",
			"              ",
			"              ",
			"            "};
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
	
	@Override
	public boolean canPieceMoveLikeThat(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ) 
	{
		Rook rook = new Rook(white);
		Bishop bishop = new Bishop(white);
		
		
		return (rook.canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)||
				bishop.canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB));
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
		
		Rook rook = new Rook(white);
		Bishop bishop = new Bishop(white);
		
		int yDiff = Math.abs(to_Y_Coordinate - from_Y_Coordinate);
		int xDiff = Math.abs(to_X_Coordinate - from_X_Coordinate);
		if(yDiff == xDiff)
			return bishop.noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);
		else
			return rook.noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);
		
	}
}
