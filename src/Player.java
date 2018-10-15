
public class Player {

	private boolean white;
	private boolean black = !white;
	private String name;
	private Piece[] pieces = new Piece[16];
	
	public Player(boolean white) {
		super();
		this.white = white;
		
		if(isWhite())
			setWhitePieces();
		else
			setBlackPieces();
	}
	
	public void setWhitePieces(){
		pieces[0] = new King("e8", white);
		pieces[1] = new Queen("d8", white);
		pieces[2] = new Bishop("f8", white);
		pieces[3] = new Bishop("c8", white);
		pieces[4] = new Horse("g8", white);
		pieces[5] = new Horse("b8", white);
		pieces[6] = new Rook("a8", white);
		pieces[7] = new Rook("h8", white);
		pieces[8] = new WhitePawn("a7", white);
		pieces[9] = new WhitePawn("b7", white);
		pieces[10] = new WhitePawn("c7", white);
		pieces[11] = new WhitePawn("d7", white);
		pieces[12] = new WhitePawn("e7", white);
		pieces[13] = new WhitePawn("f7", white);
		pieces[14] = new WhitePawn("g7", white);
		pieces[15] = new WhitePawn("h7", white);
	}
	
	public void setBlackPieces(){
		pieces[0] = new King("e1", black);
		pieces[1] = new Queen("d1", black);
		pieces[2] = new Bishop("f1", black);
		pieces[3] = new Bishop("c1", black);
		pieces[4] = new Horse("g1", black);
		pieces[5] = new Horse("b1", black);
		pieces[6] = new Rook("a1", black);
		pieces[7] = new Rook("h1", black);
		pieces[8] = new BlackPawn("a2", black);
		pieces[9] = new BlackPawn("b2", black);
		pieces[10] = new BlackPawn("c2", black);
		pieces[11] = new BlackPawn("d2", black);
		pieces[12] = new BlackPawn("e2", black);
		pieces[13] = new BlackPawn("f2", black);
		pieces[14] = new BlackPawn("g2", black);
		pieces[15] = new BlackPawn("h2", black);
	}
	public boolean isBlack() {
		return black;
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
