package chess;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public abstract class Piece implements Serializable
{
	private boolean white; // boolean to test if the piece is white or black
	private String icon[];
	private int row;
	private int col;

	public Piece(boolean white, int row, int col, String[] icon)
	{
		this.white = white;
		this.row = row;
		this.col = col;
		this.icon = icon;
	}

	// Public getters and setters
	public boolean isWhite()
	{
		return white;
	}

	public int getRow()
	{
		return row;
	}

	public int getCol()
	{
		return col;
	}

	public void setRowCol(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	public String getIcon(int line)
	{
		return icon[line];
	}

	// Main legality check
	public boolean isLegalMove(int toRow, int toCol, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard,
			Piece King, boolean surpress)
	{
		if (!isLegalCheck(toRow, toCol, pieces, chessBoard, King, surpress))
			return false;
		if (leavesKingInCheck(toRow, toCol, pieces, chessBoard, King))
		{
			if (!surpress)
				System.out.println("Warning! Leaves king in check");
			return false;
		}
		return true;
	}

	public boolean isLegalCheck(int toRow, int toCol, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard,
			Piece King, boolean surpress)
	{
		String error = null;
		if (!canPieceMoveLikeThat(toRow, toCol, chessBoard))
			error = "WARNING! Piece cannot move like that";
		else if (sameColor(toRow, toCol, chessBoard))
			error = "WARNING! Piece will kill same color";
		else if (pieceInTheWay(toRow, toCol, chessBoard))
			error = "WARNING! Piece in the way";
		else
			return true;
		if (!surpress)
			System.out.println(error);
		return false;
	}

	// Private methods for legal move check
	private boolean sameColor(int toRow, int toCol, Piece[][] chessBoard)
	{
		if (chessBoard[toRow][toCol] == null)
			return false;
		if (this.isWhite() == chessBoard[toRow][toCol].isWhite())
			return true;
		return false;
	}

	private boolean leavesKingInCheck(int toRow, int toCol, ArrayList<ArrayList<Piece>> pieces, Piece[][] chessBoard,
			Piece King)
	{
		int oldRow = this.getRow();
		int oldCol = this.getCol();
		Piece currPiece = this;
		Piece killPiece = chessBoard[toRow][toCol];
		currPiece.setRowCol(toRow, toCol);
		pieces.get(this.isWhite() ? 1 : 0).remove(killPiece);
		chessBoard[toRow][toCol] = currPiece;
		chessBoard[oldRow][oldCol] = null;
		boolean safe = false;
		if (!((King) King).inCheck(pieces, chessBoard))
			safe = true;
		if (killPiece != null)
			pieces.get(this.isWhite() ? 1 : 0).add(killPiece);
		chessBoard[toRow][toCol] = killPiece;
		chessBoard[oldRow][oldCol] = this;
		this.setRowCol(oldRow, oldCol);
		if (safe)
			return false;
		return true;
	}

	// Abstract methods needed and overridden by all pieces
	public abstract boolean pieceInTheWay(int toRow, int toCol, Piece[][] chessBoard);

	public abstract boolean canPieceMoveLikeThat(int toRow, int toCol, Piece[][] chessBoard);

	public abstract int getAIValue();
}