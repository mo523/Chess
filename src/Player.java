
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
		pieces[0] = new King("e7", white);
		pieces[1] = new Queen("d7", white);
		pieces[2] = new Bishop("f7", white);
		pieces[3] = new Bishop("c7", white);
		pieces[4] = new Horse("g7", white);
		pieces[5] = new Horse("b7", white);
		pieces[6] = new Rook("a7", white);
		pieces[7] = new Rook("h7", white);
		pieces[8] = new WhitePawn("a6", white);
		pieces[9] = new WhitePawn("b6", white);
		pieces[10] = new WhitePawn("c6", white);
		pieces[11] = new WhitePawn("d6", white);
		pieces[12] = new WhitePawn("e6", white);
		pieces[13] = new WhitePawn("f6", white);
		pieces[14] = new WhitePawn("g6", white);
		pieces[15] = new WhitePawn("h6", white);
	}
	
	public void setBlackPieces(){
		pieces[0] = new King("e0", !white);
		pieces[1] = new Queen("d0", !white);
		pieces[2] = new Bishop("f0", !white);
		pieces[3] = new Bishop("c0", !white);
		pieces[4] = new Horse("g0", !white);
		pieces[5] = new Horse("b0", !white);
		pieces[6] = new Rook("a0", !white);
		pieces[7] = new Rook("h0", !white);
		pieces[8] = new BlackPawn("a1", !white);
		pieces[9] = new BlackPawn("b1", !white);
		pieces[10] = new BlackPawn("c1", !white);
		pieces[11] = new BlackPawn("d1", !white);
		pieces[12] = new BlackPawn("e1", !white);
		pieces[13] = new BlackPawn("f1", !white);
		pieces[14] = new BlackPawn("g1", !white);
		pieces[15] = new BlackPawn("h1", !white);
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
