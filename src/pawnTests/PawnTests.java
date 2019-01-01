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
		assertTrue(chessBoard[4][0].isLegalMove(0, 4, 0, 5, chessBoard, whiteKing));
	}
	
	@Test
	public void whitePawnOneSpaceForwardDoesntWorkWhenPieceIsInTheWay() {
		assertFalse(chessBoard[5][1].isLegalMove(1, 5, 1, 1, chessBoard, whiteKing));
	}
	
	@Test
	public void whitePawnTwoSpacesForwardDoesntWorkWhenPieceIsInTheWay() {
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 0, 6, chessBoard, whiteKing));
	}
	
	@Test
	public void whitePawnCanKillFrontLeftAndRight() {
		assertTrue(chessBoard[5][1].isLegalMove(1, 5, 0, 6, chessBoard, whiteKing));
		assertTrue(chessBoard[5][1].isLegalMove(1, 5, 2, 6, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCanMoveForwardOnFirstMoveButNotSecond() {
		ChessDriver.setMovingPiece(true);
		assertTrue(chessBoard[1][7].isLegalMove(7, 1, 7, 3, chessBoard, whiteKing));
		//they are the same move because the piece never actually moved but the test thinks it did because of "ChessDriver.setMovingPiece(true);"
		assertFalse(chessBoard[1][7].isLegalMove(7, 1, 7, 3, chessBoard, whiteKing));
		assertTrue(chessBoard[1][7].isLegalMove(7, 1, 7, 2, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCantKillForward2SpacesAndForward2SpacesDiagonally() {
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 6, 6, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 6, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 6, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCantMoveBackward1SpaceAnd2SpacesAndDiagonallyAnd2SpacesBackDiagonally() {
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 6, 3, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 6, 2, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 3, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 3, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 2, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 2, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCantMoveSideways() {
		ChessDriver.setChessBoard(chessBoard);
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 4, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 4, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 3, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 4, chessBoard, whiteKing));
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 1, 4, chessBoard, whiteKing));
		
	}
	@Test
	public void whitePawnCantKillBackwardsAndDiagonallyBAckwards() {
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 0, 3, chessBoard, whiteKing));
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 1, 3, chessBoard, whiteKing));

	}
	@Test
	public void blackPawnCanMoveOneSpaceForward() {
		assertTrue(chessBoard[6][7].isLegalMove(7, 6, 7, 5, chessBoard, blackKing));
	}
	@Test
	public void blackPawnOneSpaceForwardDoesntWorkWhenPieceIsInTheWay() {
		assertFalse(chessBoard[6][1].isLegalMove(1, 6, 1, 5, chessBoard, blackKing));
	}
	
	@Test
	public void blackPawnTwoSpacesForwardDoesntWorkWhenPieceIsInTheWay() {
		assertTrue(chessBoard[6][0].isLegalMove(0, 6, 0, 5, chessBoard, blackKing));
		assertFalse(chessBoard[6][0].isLegalMove(0, 6, 0, 4, chessBoard, blackKing));
	}
	
	@Test
	public void blackPawnCanKillFrontLeftAndRight() {
		assertTrue(chessBoard[6][0].isLegalMove(0, 6, 1, 5, chessBoard, blackKing));
		assertTrue(chessBoard[6][2].isLegalMove(2, 6, 1, 5, chessBoard, blackKing));
	}
	@Test
	public void blackPawnCanMoveForwardOnFirstMoveButNotSecond() {
		ChessDriver.setMovingPiece(true);
		assertTrue(chessBoard[6][7].isLegalMove(7, 6, 7, 4, chessBoard, blackKing));
		//they are the same move because the piece never actually moved but the test thinks it did because of "ChessDriver.setMovingPiece(true);"
		assertFalse(chessBoard[6][7].isLegalMove(7, 6, 7, 4, chessBoard, blackKing));
		assertTrue(chessBoard[6][7].isLegalMove(7, 6, 7, 5, chessBoard, blackKing));
	}
	@Test
	public void blackPawnCantKillForward2SpacesAndForward2SpacesDiagonally() {
		assertFalse(chessBoard[6][6].isLegalMove(6, 6, 6, 4, chessBoard, blackKing));
		assertFalse(chessBoard[6][5].isLegalMove(6, 5, 6, 4, chessBoard, blackKing));
		assertFalse(chessBoard[6][7].isLegalMove(7, 6, 6, 4, chessBoard, blackKing));
	}
	/*@Test
	public void blackPawnCantMoveBackward1SpaceAnd2SpacesAndDiagonallyAnd2SpacesBackDiagonally() {
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 6, 3, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 6, 2, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 3, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 3, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 2, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 2, chessBoard, blackKing));
	}
	@Test
	public void blackPawnCantMoveSideways() {
		ChessDriver.setChessBoard(chessBoard);
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 4, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 4, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 3, chessBoard, blackKing));
		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 4, chessBoard, blackKing));
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 1, 4, chessBoard, blackKing));
		
	}
	@Test
	public void blackPawnCantKillBackwardsAndDiagonallyBAckwards() {
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 0, 3, chessBoard, blackKing));
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 1, 3, chessBoard, blackKing));

	}*/
	@Before
	public void setUpPieces() {
		final boolean IS_WHITE = true;// this is to make what color the pieces are more clear
		final boolean IS_BLACK = false;// this is to make what color the pieces are more clear
		chessBoard = new Piece[8][8];
		//whitesTurn = true;
		chessBoard[3][0] = new Pawn(IS_BLACK);
		chessBoard[3][1] = new Pawn(IS_BLACK);
		chessBoard[4][0] = new Pawn(IS_WHITE);
		chessBoard[5][1] = new Pawn(IS_WHITE);
		chessBoard[1][2] = new Pawn(IS_WHITE);
		chessBoard[1][3] = new Pawn(IS_WHITE);
		chessBoard[1][4] = new Pawn(IS_WHITE);
		chessBoard[1][5] = new Pawn(IS_WHITE);
		chessBoard[4][6] = new Pawn(IS_WHITE);
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
