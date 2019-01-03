package chess;

import java.io.Serializable;
import java.util.ArrayList;
import exceptions.InvalidPieceException;

@SuppressWarnings("serial")
public abstract class Piece implements Serializable {

	private boolean white; // boolean to test if the piece is white or black
	private String icon[];
	private int row;
	private int col;

	/**
	 * @param white tells if the piece is white or black
	 */
	public Piece(boolean white, int row, int col, String[] icon) {
		this.white = white;
		this.row = row;
		this.col = col;
		this.icon = icon;
	}

	// Public getters and setters
	public boolean isWhite() {
		return white;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRowCol(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public String getIcon(int line) {
		return icon[line];
	}

	// Main legality check
	public boolean isLegalMove(int toRow, int toCol, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard,
			Piece King) {
		if (!canPieceMoveLikeThat(toRow, toCol, chessBoard)) {
			ChessDriver.setErrorMessage("WARNING! Piece cannot move like that");
			return false;
		}
		if (sameColor(toRow, toCol, chessBoard)) {
			ChessDriver.setErrorMessage("WARNING! Piece will kill same color");
			return false;
		}
		if (pieceInTheWay(toRow, toCol, chessBoard)) {
			ChessDriver.setErrorMessage("WARNING! Piece in the way");
			return false;
		}
		if (leavesKingInCheck(toRow, toCol, pieces, chessBoard, King)) {
			ChessDriver.setErrorMessage("Warning! Leaves king in check");
			return false;
		}
		return true;
	}
	
	public boolean isLegalerMove(int toRow, int toCol, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard,
			Piece King) {
		if (!canPieceMoveLikeThat(toRow, toCol, chessBoard)) {
			ChessDriver.setErrorMessage("WARNING! Piece cannot move like that");
			return false;
		}
		if (sameColor(toRow, toCol, chessBoard)) {
			ChessDriver.setErrorMessage("WARNING! Piece will kill same color");
			return false;
		}
		if (pieceInTheWay(toRow, toCol, chessBoard)) {
			ChessDriver.setErrorMessage("WARNING! Piece in the way");
			return false;
		}
		return true;}

	// Private methods for legal move check
	private boolean sameColor(int toRow, int toCol, Piece[][] chessBoard) {
		if (chessBoard[toRow][toCol] == null)
			return false;
		if (this.isWhite() == chessBoard[toRow][toCol].isWhite())
			return true;
		return false;
	}

	private boolean leavesKingInCheck(int toRow, int toCol, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard,
			Piece King) {
		if (this.isLegalerMove(toRow, toCol, pieces, chessBoard, this)) {
			int oldRow = this.getRow();
			int oldCol = this.getCol();
			Piece currPiece = this;
			Piece killPiece = chessBoard[toRow][toCol];
			currPiece.setRowCol(toRow, toCol);
			pieces.get(this.isWhite() ? 1 : 0).remove(killPiece);
			chessBoard[toRow][toCol] = currPiece;
			chessBoard[oldRow][oldCol] = null;
			boolean safe = false;
			if (!this.inCheck(this, pieces, chessBoard))
				safe = true;
			pieces.get(this.isWhite() ? 1 : 0).add(killPiece);
			chessBoard[toRow][toCol] = killPiece;
			chessBoard[oldRow][oldCol] = this;
			this.setRowCol(oldRow, oldCol);
			if (safe)
				return false;
		}
		return true;
	}

	public Piece[][] makeNewBoard(Piece[][] chessBoard) throws InvalidPieceException {
		Piece[][] newchessBoard = new Piece[8][8];
		Piece curr;
		for (int i = 0; i < chessBoard.length; i++) {
			for (int j = 0; j < chessBoard[i].length; j++) {
				curr = chessBoard[i][j];
				if (curr instanceof Pawn) {
					try {
						newchessBoard[i][j] = ((Pawn) curr).clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				} else if (curr instanceof Queen)
					newchessBoard[i][j] = new Queen(curr.isWhite(), curr.getRow(), curr.getCol());
				else if (curr instanceof Rook)
					newchessBoard[i][j] = new Rook(curr.isWhite(), curr.getRow(), curr.getCol());
				else if (curr instanceof Bishop)
					newchessBoard[i][j] = new Bishop(curr.isWhite(), curr.getRow(), curr.getCol());
				else if (curr instanceof Horse)
					newchessBoard[i][j] = new Horse(curr.isWhite(), curr.getRow(), curr.getCol());
				else if (curr instanceof King)
					newchessBoard[i][j] = new King(curr.isWhite(), curr.getRow(), curr.getCol());
				else if (curr == null)
					;// do nothing
				else
					throw new InvalidPieceException("That piece does not exist");
			}
		}
		return newchessBoard;
	}

	// Abstract methods needed and overridden by all pieces
	public abstract boolean pieceInTheWay(int toRow, int toCol, Piece[][] chessBoard);

	public abstract boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] chessBoard);

	// Methods needed for and overridden by King
	public boolean checkmate(ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard) {
		return false;
	}

	public boolean inCheck(Piece king, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard) {
		return false;
	}

	// Methods needed for and overridden by Pawn
	public boolean enPassantAble() {
		return false;
	}

	public void setEnPassant(boolean enp) {
	}

}