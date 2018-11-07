public abstract class Piece {

	String position; // this is the position in the array of the game board (e.g. 06 or 77)
	String name; // name of the piece
	boolean white; // boolean to test if the piece is white or black
	char displayCharacter; // this is the character that will be displayed on the screen in the board
	String icon[];
	
	/**
	 * 
	 * @param position is the position of the piece
	 * @param white tells if the piece is white or black
	 */
	public Piece(boolean white) {
		this.white = white;
		
	}
	
	
	//all other methods go in this one
	public boolean isLegalMove(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ){
		/*if(!canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB))
			System.out.println("WARNING piece cant move like that");
		if(!willNotKillSameColor(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB ))
			System.out.println("WARNING piece will kill same color");
		if(!noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB))
			System.out.println("WARNING piece in the way");*/
		
		
		
		return canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
				&& willNotKillSameColor(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB )
				&& noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
				&& doesntLeaveKingInCheck(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);

	}
		
	public boolean doesntLeaveKingInCheck(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB){
		int xCoordinate = 0;
		int yCoordinate = 0;
		boolean done = false;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if(CB[i][j] instanceof King && CB[i][j].isWhite() == this.isWhite()){
					yCoordinate = i;
					xCoordinate = j;
					done = true;
					break;
				}
			}
			if(done)
				break;
		}
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if(moveCheckForCheck(j, i, xCoordinate, yCoordinate, CB))
					return false;
		return true;

	}
	private boolean moveCheckForCheck(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB){
		if(CB[from_X_Coordinate][from_Y_Coordinate] != null)
			return CB[from_X_Coordinate][from_Y_Coordinate].canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
				&& CB[from_X_Coordinate][from_Y_Coordinate].willNotKillSameColor(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
				&& CB[from_X_Coordinate][from_Y_Coordinate].noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);
		return false;
	}
	public abstract boolean noPieceInTheWay(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB );
//	public boolean inCheck(String from, String to){
//		
//	}

	
	public abstract boolean canPieceMoveLikeThat(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB );
	//method works
	public boolean willNotKillSameColor(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ) {
		if(CB[to_Y_Coordinate][to_X_Coordinate] == null)
			return true;
		if(this.isWhite() == CB[to_Y_Coordinate][to_X_Coordinate].isWhite())
			return false;
		return true;
	}
	
	public boolean isWhite() {
		return white;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public String getIcon(int line)
	{
		return icon[line];
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	public char getDisplayCharacter() {
		return white ? Character.toUpperCase(displayCharacter) : Character.toLowerCase(displayCharacter);
	}

	
}
