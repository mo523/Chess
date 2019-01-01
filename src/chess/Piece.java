package chess;
import java.io.Serializable;

import exceptions.InvalidPieceException;
@SuppressWarnings("serial")
public abstract class Piece implements Serializable{

	protected String name; // name of the piece
	protected boolean white; // boolean to test if the piece is white or black
	protected String icon[];
	
	
	/**
	 * @param white tells if the piece is white or black
	 */
	public Piece(boolean white) {
		this.white = white;
	}
	
	public boolean isWhite() {
		return white;
	}
	public String getIcon(int line){
		return icon[line];
	}
	
	//all other methods go in this one
	public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB, Piece King){
		if(!canPieceMoveLikeThat(fromRow, fromCol, toRow, toCol, CB)){
			ChessDriver.setErrorMessage("WARNING! Piece cannot move like that");
			return false;
		}
		if(!willNotKillSameColor(fromRow, fromCol, toRow, toCol, CB )){
			ChessDriver.setErrorMessage("WARNING! Piece will kill same color");
			return false;
		}
		if(!noPieceInTheWay(fromRow, fromCol, toRow, toCol, CB)){
			ChessDriver.setErrorMessage("WARNING! Piece in the way");
			return false;
		}
		if (!doesntLeaveKingInCheck(fromRow, fromCol, toRow, toCol, CB, King)){
			ChessDriver.setErrorMessage("Warning! Leaves king in check");
			return false;
		}		
		return true;
	}
	
	public boolean inCheck(Piece King, Piece[][] CB ){
		int y = 0, x = 0;
		
		outer: for ( y = 0; y < 8; y++)
			for (x = 0; x < 8; x++)
				if (CB[y][x] instanceof King && CB[y][x].isWhite() == this.isWhite())
					break outer;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) 
				if(moveCheckForCheck(i, j, y, x, CB)) 
					return true;//can kill
		return false;
	}
	
	public boolean doesntLeaveKingInCheck(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB, Piece King){
		//makes a temporary board and moves the piece in it
		Piece[][] newCB = null;
		try {
			newCB = makeNewBoard(CB);
		} catch (InvalidPieceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			newCB[toRow][toCol] = newCB[fromRow][fromCol];
			newCB[fromRow][fromCol] = null;
		return !inCheck(King, newCB);
	}
	public boolean notInCheckmate(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB, Piece King){
		//makes a temporary board and moves the piece in it
		Piece[][] newCB = null;
		try {
			newCB = makeNewBoard(CB);
		} catch (InvalidPieceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (moveCheckForCheck( fromRow,  fromCol,  toRow,  toCol, newCB))
		{
			newCB[toRow][toCol] = newCB[fromRow][fromCol];
			newCB[fromRow][fromCol] = null;
			return !inCheck(King, newCB);
			}
		else
			return false;
	}
	private Piece[][] makeNewBoard(Piece[][] CB) throws InvalidPieceException{
		Piece[][] newCB = new Piece[8][8];
		for (int i = 0; i < CB.length; i++) {
			for (int j = 0; j < CB[i].length; j++) {
				if(CB[i][j] instanceof Pawn) {
					try {
						newCB[i][j] = ((Pawn) CB[i][j]).clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
				else if(CB[i][j] instanceof Queen)
					newCB[i][j] = new Queen(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Rook)
					newCB[i][j] = new Rook(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Bishop)
					newCB[i][j] = new Bishop(CB[i][j].isWhite());
				else if(CB[i][j] instanceof Horse)
					newCB[i][j] = new Horse(CB[i][j].isWhite());
				else if(CB[i][j] instanceof King)
					newCB[i][j] = new King(CB[i][j].isWhite());
				else if(CB[i][j] == null);//do nothing
				else
					throw new InvalidPieceException("That piece does not exist");
			}	
		}
		return newCB;
	}
	private boolean moveCheckForCheck(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB){
		if(CB[fromRow][fromCol] != null)
			return CB[fromRow][fromCol].canPieceMoveLikeThat(fromRow, fromCol, toRow, toCol, CB)
				&& CB[fromRow][fromCol].willNotKillSameColor(fromRow, fromCol, toRow, toCol, CB)
				&& CB[fromRow][fromCol].noPieceInTheWay(fromRow, fromCol, toRow, toCol, CB);
		return false;
	}
	public abstract boolean noPieceInTheWay(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB );

	public abstract boolean canPieceMoveLikeThat(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB );
	//method works
	public boolean willNotKillSameColor(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB ) {
		if(CB[toRow][toCol] == null)
			return true;
		if(this.isWhite() == CB[toRow][toCol].isWhite())
			return false;
		return true;
	}
	
	public String isInstanceOf() {
		return name;
	}

	protected abstract boolean isEnPassantAble();
}