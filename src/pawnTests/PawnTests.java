package pawnTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import chess.*;

public class PawnTests {

	Piece[][] chessBoard;
	//private ChessBoard CB;
	ArrayList<ArrayList<Piece>> pieces;
	private static Piece whiteKing, blackKing;
	
	public PawnTests() {
		setUpPieces();
		//CB = new ChessBoard(false,false,false,false, false, false);
		setUpArray();
	}

	 
	private void setUpArray() {
		pieces = new ArrayList<ArrayList<Piece>>();
		pieces.add(new ArrayList<Piece>());
		pieces.add(new ArrayList<Piece>());
		for (Piece[] pa : chessBoard)
			for (Piece currPiece : pa)
				if (currPiece != null)
					pieces.get(currPiece.isWhite() ? 0 : 1).add(currPiece);
	}
	
	@Test
	public void whitePawnOneSpaceForwardWorks() {
		Display.debug(chessBoard);
		assertTrue(chessBoard[4][0].isLegalMove( 5,0, pieces, chessBoard, whiteKing));
	}
	
	@Test
	public void whitePawnOneSpaceForwardDoesntWorkWhenPieceIsInTheWay() {
		assertFalse(chessBoard[5][1].isLegalMove(1, 1, pieces, chessBoard, whiteKing));
	}
	
	@Test
	public void whitePawnTwoSpacesForwardDoesntWorkWhenPieceIsInTheWay() {
		assertFalse(chessBoard[4][0].isLegalMove(  6,0, pieces, chessBoard, whiteKing));
	}
	
	@Test
	public void whitePawnCanKillFrontLeftAndRight() {
		assertTrue(chessBoard[5][1].isLegalMove(  6,0, pieces, chessBoard, whiteKing));
		assertTrue(chessBoard[5][1].isLegalMove(  6,2, pieces, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCanMoveForwardOnFirstMoveButNotSecond() {
		//ChessDriver.setMovingPiece(true);
		assertTrue(chessBoard[1][7].isLegalMove(  3,7, pieces, chessBoard, whiteKing));
		assertTrue(chessBoard[1][7].isLegalMove(  2,7, pieces, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCantKillForward2SpacesAndForward2SpacesDiagonally() {
		assertFalse(chessBoard[4][6].isLegalMove(  6,6, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  6,5, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  6,7, pieces, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCantMoveBackward1SpaceAnd2SpacesAndDiagonallyAnd2SpacesBackDiagonally() {
		assertFalse(chessBoard[4][6].isLegalMove(  3,6, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  2,6, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  3,5, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  3, 7,pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  2,5, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  2,7, pieces, chessBoard, whiteKing));
	}
	@Test
	public void whitePawnCantMoveSideways() {
		//ChessDriver.setChessBoard(chessBoard);
		assertFalse(chessBoard[4][6].isLegalMove(  4,7, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  4,5, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  3,5, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][6].isLegalMove(  4,7, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][0].isLegalMove(  4,1, pieces, chessBoard, whiteKing));
		
	}
	@Test
	public void whitePawnCantKillBackwardsAndDiagonallyBAckwards() {
		assertFalse(chessBoard[4][0].isLegalMove( 3,0, pieces, chessBoard, whiteKing));
		assertFalse(chessBoard[4][0].isLegalMove(  3,1, pieces, chessBoard, whiteKing));

	}
	@Test
	public void blackPawnCanMoveOneSpaceForward() {
		assertTrue(chessBoard[6][7].isLegalMove(  5,7, pieces, chessBoard, blackKing));
	}
	@Test
	public void blackPawnOneSpaceForwardDoesntWorkWhenPieceIsInTheWay() {
		assertFalse(chessBoard[6][1].isLegalMove(  5,1, pieces, chessBoard, blackKing));
	}
	
	@Test
	public void blackPawnTwoSpacesForwardDoesntWorkWhenPieceIsInTheWay() {
		assertTrue(chessBoard[6][0].isLegalMove(  5,0, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[6][0].isLegalMove(  4,0, pieces, chessBoard, blackKing));
	}
	
	@Test
	public void blackPawnCanKillFrontLeftAndRight() {
		assertTrue(chessBoard[6][0].isLegalMove(  5,1, pieces, chessBoard, blackKing));
		assertTrue(chessBoard[6][2].isLegalMove(  5,1, pieces, chessBoard, blackKing));
	}
	@Test
	public void blackPawnCanMoveForwardOnFirstMoveButNotSecond() {
		assertTrue(chessBoard[6][7].isLegalMove(  4,7, pieces, chessBoard, blackKing));
		assertTrue(chessBoard[6][7].isLegalMove(  5,7, pieces, chessBoard, blackKing));
	}
	@Test
	public void blackPawnCantKillForward2SpacesAndForward2SpacesDiagonally() {
		assertFalse(chessBoard[6][6].isLegalMove( 4, 6, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[6][5].isLegalMove( 4, 6, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[6][7].isLegalMove(  4,6, pieces, chessBoard, blackKing));
	}
	@Test
	public void blackPawnCantMoveBackward1SpaceAnd2SpacesAndDiagonallyAnd2SpacesBackDiagonally() {
		assertFalse(chessBoard[4][3].isLegalMove(5,3, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[4][3].isLegalMove(6, 3, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[4][3].isLegalMove( 5, 2, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[4][3].isLegalMove( 5, 4, pieces, chessBoard, blackKing));
//		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 2, pieces, chessBoard, blackKing));
//		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 2, pieces, chessBoard, blackKing));
	}
	@Test
	public void blackPawnCantMoveSideways() {
		assertFalse(chessBoard[4][3].isLegalMove( 4, 5, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[4][3].isLegalMove( 4, 5, pieces, chessBoard, blackKing));
//		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 5, 3, pieces, chessBoard, blackKing));
//		assertFalse(chessBoard[4][6].isLegalMove(6, 4, 7, 4, pieces, chessBoard, blackKing));
//		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 1, 4, pieces, chessBoard, blackKing));
		
	}
	/*@Test
	public void blackPawnCantKillBackwardsAndDiagonallyBAckwards() {
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 0, 3, pieces, chessBoard, blackKing));
		assertFalse(chessBoard[4][0].isLegalMove(0, 4, 1, 3, pieces, chessBoard, blackKing));

	}*/	

	public void setUpPieces() {
		final boolean IS_WHITE = true;// this is to make what color the pieces are more clear
		final boolean IS_BLACK = false;// this is to make what color the pieces are more clear
		chessBoard = new Piece[8][8];
		//whitesTurn = true;
		chessBoard[3][0] = new Pawn(IS_BLACK,3,0);
		chessBoard[3][1] = new Pawn(IS_BLACK,3,1);
		chessBoard[4][0] = new Pawn(IS_WHITE,4,0);
		chessBoard[5][1] = new Pawn(IS_WHITE,5,1);
		chessBoard[1][2] = new Pawn(IS_WHITE,1,2);
		chessBoard[1][3] = new Pawn(IS_WHITE,1,3);
		chessBoard[1][4] = new Pawn(IS_WHITE,1,4);
		chessBoard[1][5] = new Pawn(IS_WHITE,1,5);
		chessBoard[4][6] = new Pawn(IS_WHITE,4,6);
		chessBoard[1][7] = new Pawn(IS_WHITE,1,7);
		chessBoard[6][0] = new Pawn(IS_BLACK,6,0);
		chessBoard[6][1] = new Pawn(IS_BLACK,6,1);
		chessBoard[6][2] = new Pawn(IS_BLACK,6,2);
		chessBoard[4][3] = new Pawn(IS_BLACK,4,3);
		chessBoard[6][4] = new Pawn(IS_BLACK,6,4);
		chessBoard[6][5] = new Pawn(IS_BLACK,6,5);
		chessBoard[6][6] = new Pawn(IS_BLACK,6,6);
		chessBoard[6][7] = new Pawn(IS_BLACK,6,7);
		chessBoard[0][0] = new Rook(IS_WHITE,0,0);
		chessBoard[0][1] = new Horse(IS_WHITE,0,1);
		chessBoard[0][2] = new Bishop(IS_WHITE,0,2);
		chessBoard[0][3] = new King(IS_WHITE,0,3);
		chessBoard[0][4] = new Queen(IS_WHITE,0,4);
		chessBoard[0][5] = new Bishop(IS_WHITE,0,5);
		chessBoard[0][6] = new Horse(IS_WHITE,0,6);
		chessBoard[0][7] = new Rook(IS_WHITE,0,7);
		chessBoard[7][0] = new Rook(IS_BLACK,7,0);
		chessBoard[7][1] = new Horse(IS_BLACK,7,1);
		chessBoard[7][2] = new Bishop(IS_BLACK,7,2);
		chessBoard[7][3] = new King(IS_BLACK,7,3);
		chessBoard[7][4] = new Queen(IS_BLACK,7,4);
		chessBoard[7][5] = new Bishop(IS_BLACK,7,5);
		chessBoard[7][6] = new Horse(IS_BLACK,7,6);
		chessBoard[7][7] = new Rook(IS_BLACK,7,7);
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
