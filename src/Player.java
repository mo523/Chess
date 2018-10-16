
public class Player {

	private boolean white;
	private String name;
	private Piece[] pieces = new Piece[16];
	static final  int Max_Num_Of_Pieces = 16;
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
	
	
	
	public void setWhitePieces(){
		pieces[0] = new King("47", white);
		pieces[1] = new Queen("37", white);
		pieces[2] = new Bishop("57", white);
		pieces[3] = new Bishop("27", white);
		pieces[4] = new Horse("67", white);
		pieces[5] = new Horse("17", white);
		pieces[6] = new Rook("07", white);
		pieces[7] = new Rook("77", white);
		pieces[8] = new WhitePawn("06", white);
		pieces[9] = new WhitePawn("16", white);
		pieces[10] = new WhitePawn("26", white);
		pieces[11] = new WhitePawn("36", white);
		pieces[12] = new WhitePawn("46", white);
		pieces[13] = new WhitePawn("56", white);
		pieces[14] = new WhitePawn("66", white);
		pieces[15] = new WhitePawn("76", white);
	}
	
	public void setBlackPieces(){
		pieces[0] = new King("40", !white);
		pieces[1] = new Queen("30", !white);
		pieces[2] = new Bishop("50", !white);
		pieces[3] = new Bishop("20", !white);
		pieces[4] = new Horse("60", !white);
		pieces[5] = new Horse("10", !white);
		pieces[6] = new Rook("00", !white);
		pieces[7] = new Rook("70", !white);
		pieces[8] = new BlackPawn("01", !white);
		pieces[9] = new BlackPawn("11", !white);
		pieces[10] = new BlackPawn("21", !white);
		pieces[11] = new BlackPawn("31", !white);
		pieces[12] = new BlackPawn("41", !white);
		pieces[13] = new BlackPawn("51", !white);
		pieces[14] = new BlackPawn("61", !white);
		pieces[15] = new BlackPawn("71", !white);
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



	
}
