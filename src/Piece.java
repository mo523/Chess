public abstract class Piece {

	protected String name; // name of the piece
	protected boolean white; // boolean to test if the piece is white or black
	protected String icon[];
	
	/**
	 * 
	 * @param position is the position of the piece
	 * @param white tells if the piece is white or black
	 */
	public Piece(boolean white) {
		this.white = white;
	}
	
	public boolean isWhite() {
		return white;
	}
	public String getIcon(int line)
	{
		return icon[line];
	}
	
	//all other methods go in this one
	public boolean isLegalMove(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB, Piece King){
		if(!canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB))
		{
			System.out.println("WARNING! Piece cannot move like that");
			return false;
		}
		if(!willNotKillSameColor(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB ))
		{
			System.out.println("WARNING! Piece will kill same color");
			return false;
		}
		if(!noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB))
		{
			System.out.println("WARNING! Piece in the way");
			return false;
		}
		if (!doesntLeaveKingInCheck(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB, King))
			{
			System.out.println("Warning! Leaves king in check");
			return false;
			}		
		return true;
	}
	
	public boolean inCheck(Piece King, Piece[][] CB )
	{
		int y = 0, x = 0;
		
		outer: for ( y = 0; y < 8; y++)
			for (x = 0; x < 8; x++)
				if (CB[y][x] instanceof King && CB[y][x].isWhite() == this.isWhite())
					break outer;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if(moveCheckForCheck(j, i, x, y, CB))
					return true;//can kill
		return false;
	}
	
	public boolean doesntLeaveKingInCheck(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB, Piece King){
		//makes a temporary board and moves the piece in it
		Piece[][] newCB = makeNewBoard(CB);
		ChessDriver.performMove(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, newCB);
		return !inCheck(King, newCB);
	}
	public boolean notInCheckmate(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB, Piece King){
		//makes a temporary board and moves the piece in it
		Piece[][] newCB = makeNewBoard(CB);
		if (moveCheckForCheck( from_X_Coordinate,  from_Y_Coordinate,  to_X_Coordinate,  to_Y_Coordinate, newCB))
		{
		ChessDriver.performMove(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, newCB);
		return !inCheck(King, newCB);}
		else
			return false;
	}
	private Piece[][] makeNewBoard(Piece[][] CB){
		Piece[][] newCB = new Piece[8][8];
		for (int i = 0; i < CB.length; i++) {
			for (int j = 0; j < CB[i].length; j++) {
				if(CB[i][j] instanceof King)
					newCB[i][j] = new King(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Queen)
					newCB[i][j] = new Queen(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Rook)
					newCB[i][j] = new Rook(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Bishop)
					newCB[i][j] = new Bishop(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Horse)
					newCB[i][j] = new Horse(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Pawn)
					newCB[i][j] = new Pawn(CB[i][j].isWhite());
			}	
		}
		return newCB;
	}
	private boolean moveCheckForCheck(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB){
		if(CB[from_Y_Coordinate][from_X_Coordinate] != null)
			return CB[from_Y_Coordinate][from_X_Coordinate].canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
				&& CB[from_Y_Coordinate][from_X_Coordinate].willNotKillSameColor(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
				&& CB[from_Y_Coordinate][from_X_Coordinate].noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);
		return false;
	}
	public abstract boolean noPieceInTheWay(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB );


	
	public abstract boolean canPieceMoveLikeThat(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB );
	//method works
	public boolean willNotKillSameColor(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ) {
		if(CB[to_Y_Coordinate][to_X_Coordinate] == null)
			return true;
		if(this.isWhite() == CB[to_Y_Coordinate][to_X_Coordinate].isWhite())
			return false;
		return true;
	}
}