import java.util.Arrays;

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
				&& doesntLeaveKingInCheck(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
				;
	}
	

	public boolean doesntLeaveKingInCheck(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB){
		//makes a temporary board and moves the piece in it
		Piece[][] newCB = makeNewBoard(CB);
		ChessDriver.performMove(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, newCB);
		
		//gets kings position
		int xCoordinate = 0;
		int yCoordinate = 0;
		boolean done = false;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if(newCB[i][j] instanceof King && newCB[i][j].isWhite() == this.isWhite()){
					yCoordinate = i;
					xCoordinate = j;
					done = true;
					break;
				}
			}
			if(done)
				break;
		}
		
		//checks if any piece can kill the king
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if(moveCheckForCheck(j, i, xCoordinate, yCoordinate, newCB)){
					System.out.println("WARNING leaves king in check");
					return false;//can kill
				}	
		return true;// can't kill

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

/*String piece = CB[i][j].getName();
				switch (piece) {
				case  "King":
					newCB[i][j] = new King(CB[i][j].isWhite());
					break;
				case "Queen":
					newCB[i][j] = new Queen(CB[i][j].isWhite());
					break;
				case "Rook":
					newCB[i][j] = new Rook(CB[i][j].isWhite());
					break;
				case "Bishop":
					newCB[i][j] = new Bishop(CB[i][j].isWhite());
					break;
				case "Horse":
					newCB[i][j] = new Horse(CB[i][j].isWhite());
					break;
				case "Pawn":
					newCB[i][j] = new Pawn(CB[i][j].isWhite());
					break;
				}*/
