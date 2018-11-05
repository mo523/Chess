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
				&& noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);

	}
		
	
	
	public abstract boolean noPieceInTheWay(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB );
//	public boolean inCheck(String from, String to){
//		
//	}
//	public boolean leavesKingInCheck(String from, String to){
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
