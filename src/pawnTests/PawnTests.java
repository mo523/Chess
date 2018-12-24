package pawnTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chess.*;

public class PawnTests {

	public PawnTests() {
	}

	private static Piece[][] chessBoard;
	private static Piece whiteKing, blackKing;

	
	@Test
	public void whitePawnOneSpaceForwardWorks() {
		Display.debug(chessBoard, true, false, 1, 1, 1, 1);
		assertTrue(true);
	}
	
	@Before
	public void setUpPieces() {
		final boolean IS_WHITE = true;// this is to make what color the pieces are more clear
		final boolean IS_BLACK = false;// this is to make what color the pieces are more clear
		chessBoard = new Piece[8][8];
		//whitesTurn = true;
		
		// for tests only
		// chessBoard[2][4] = new King(IS_WHITE);
		// chessBoard[5][1] = new Bishop(IS_BLACK);
		// chessBoard[3][3] = new Pawn(IS_WHITE);
		// for tests only
		// MEYER!! See below for more tests
		// Just uncomment them out
		/*
		 * chessBoard[3][3] = new King(true); chessBoard[2][2] = new Rook(false);
		 * chessBoard[4][4] = new Rook(false); chessBoard[2][4] = new Rook(false);
		 * chessBoard[4][2] = new Rook(false);
		 */
		// chessBoard[0][0] = new Pawn(true);

		chessBoard[4][0] = new Pawn(IS_WHITE);
		chessBoard[5][1] = new Pawn(IS_WHITE);
		chessBoard[1][2] = new Pawn(IS_WHITE);
		chessBoard[1][3] = new Pawn(IS_WHITE);
		chessBoard[1][4] = new Pawn(IS_WHITE);
		chessBoard[1][5] = new Pawn(IS_WHITE);
		chessBoard[1][6] = new Pawn(IS_WHITE);
		chessBoard[1][7] = new Pawn(IS_WHITE);
		chessBoard[6][0] = new Pawn(IS_BLACK);
		chessBoard[6][1] = new Pawn(IS_BLACK);
		chessBoard[6][2] = new Pawn(IS_BLACK);
		chessBoard[6][3] = new Pawn(IS_BLACK);
		chessBoard[6][4] = new Pawn(IS_BLACK);
		chessBoard[6][5] = new Pawn(IS_BLACK);
		chessBoard[6][6] = new Pawn(IS_BLACK);
		chessBoard[6][7] = new Pawn(IS_BLACK);
		chessBoard[0][0] = new Rook(IS_WHITE);
		chessBoard[0][1] = new Horse(IS_WHITE);
		chessBoard[0][2] = new Bishop(IS_WHITE);
		chessBoard[0][3] = new King(IS_WHITE);
		chessBoard[0][4] = new Queen(IS_WHITE);
		chessBoard[0][5] = new Bishop(IS_WHITE);
		chessBoard[0][6] = new Horse(IS_WHITE);
		chessBoard[0][7] = new Rook(IS_WHITE);
		chessBoard[7][0] = new Rook(IS_BLACK);
		chessBoard[7][1] = new Horse(IS_BLACK);
		chessBoard[7][2] = new Bishop(IS_BLACK);
		chessBoard[7][3] = new King(IS_BLACK);
		chessBoard[7][4] = new Queen(IS_BLACK);
		chessBoard[7][5] = new Bishop(IS_BLACK);
		chessBoard[7][6] = new Horse(IS_BLACK);
		chessBoard[7][7] = new Rook(IS_BLACK);
		whiteKing = chessBoard[0][3];
		blackKing = chessBoard[7][3];
		// // debug for checkmate
		// chessBoard[6][4] = chessBoard[0][3];
		// chessBoard[6][5] = chessBoard[0][5];
		// chessBoard[6][3] = chessBoard[0][0];
		// chessBoard[0][0] = null;
		// chessBoard[0][5] = null;
		// chessBoard[2][4] = null;
	}



	 
}
