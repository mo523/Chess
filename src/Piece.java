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
	public boolean isLegalMove(int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate, Piece[][] CB ){
		return canPieceMoveLikeThat(from_Y_Coordinate, from_X_Coordinate, to_Y_Coordinate, to_X_Coordinate, CB );
	}
		
	public abstract boolean canPieceMoveLikeThat(int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate, int to_X_Coordinate, Piece[][] CB);
	
//	public boolean pieceInTheWay(String from, String to){
//		
//	}
//	public boolean inCheck(String from, String to){
//		
//	}
//	public boolean leavesKingInCheck(String from, String to){
//		
//	}
//	public boolean willNotKillSameColor(String from, String to) {
//		
//	}

	
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
