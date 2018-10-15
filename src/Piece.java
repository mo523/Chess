
public abstract class Piece {

	String position; // this is the position as seen on the game board (e.g. b6 or h3)
	String positionForBoard; // this is the position that has the actual values used in the arrays of the game board
	String name; // name of the piece
	boolean white; // boolean to test if the piece is white or black
	char displayCharacter; // this is the character that will be displayed on the screen in the board
	
	
	/**
	 * 
	 * @param position is the position of the piece
	 * @param white tells if the piece is white or black
	 */
	public Piece(String position, boolean white) {
		this.position = position;
		this.white = white;
		this.positionForBoard = convertPositionToNumbers();
	}

	public void setPositionForBoard(String positionForBoard) {
		this.positionForBoard = positionForBoard;
	}
	//all other methods go in this one
	public boolean isLegalMove(String from, String to){
		
	}
	public boolean canPieceMoveLikeThat(String from, String to){
	
	}
	public boolean pieceInTheWay(String from, String to){
		
	}
	public boolean inCheck(String from, String to){
		
	}
	public boolean leavesKingInCheck(String from, String to){
		
	}
	public void move(String from, String to){
		
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
	public String getPositionForBoard() {
		return positionForBoard;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public char getDisplayCharacter() {
		return white ? Character.toUpperCase(displayCharacter) : Character.toLowerCase(displayCharacter);
	}
	public String convertPositionToNumbers(){
		String positionInNumbers = "";
		switch(position.charAt(0)){
		case 'a' :
			positionInNumbers += "0";
			break;
		case 'b' :
			positionInNumbers += "1";
			break;
		case 'c' :
			positionInNumbers += "2";
			break;
		case 'd' :
			positionInNumbers += "3";
			break;
		case 'e' :
			positionInNumbers += "4";
			break;
		case 'f' :
			positionInNumbers += "5";
			break;
		case 'g' :
			positionInNumbers += "6";
			break;
		case 'h' :
			positionInNumbers += "7";
			break;
		}
		positionInNumbers += Integer.toString((Integer.parseInt(position.substring(1)) - 1));
		return positionInNumbers;
	}
	
}
