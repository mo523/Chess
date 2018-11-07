
public class Player {

	public static void main(String[] args) {
		Piece k = new King(true);
		System.out.println(k instanceof Queen);
	}
	/*private boolean white;
	private String name;
	public Player(boolean white, String name) {
		this.white = white;
		this.name = name;
		if(white)
			setWhitePieces();
		else
			setBlackPieces();
	}
	
	public boolean movePiece(String from, String to){
		boolean pieceFoundAndMoveIsLegal = false;
		for(int i = 0; i < Max_Num_Of_Pieces; i++){
			if(pieces[i].getPosition().equalsIgnoreCase(from) && pieces[i].isLegalMove(from, to)){
				pieceFoundAndMoveIsLegal = true;
				pieces[i].setPosition(to);
				break;
			}	
		}
		return pieceFoundAndMoveIsLegal;
	}
	
	public boolean isInCheckMate(){
		
	}
	
	public boolean isNotInCheckMate(){
		return !isInCheckMate();
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

	public Piece[] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[] pieces) {
		this.pieces = pieces;
	}


*/
	
}
