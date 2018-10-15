
public abstract class Piece {

	String position;
	String name;
	boolean white;
	char displayCharacter;
	
	public boolean isLegalMove(String from, String to){
		
	}
	public boolean canPieceMoveLikeThat(String from, String to){
	
	}
	public boolean pieceInTheWay(String from, String to){
		
	}
	public boolean inCheck(String from, String to){
		
	}
	public void move(String from, String to){
		
	}
	public Piece(String position, boolean white) {
		this.position = position;
		this.white = white;
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
		return convertPositionToNumbers();
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
