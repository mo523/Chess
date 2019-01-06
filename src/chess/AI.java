package chess;

import java.util.ArrayList;
import java.util.HashMap;

public class AI
{
	public static void cpuMovePiece(ChessBoard CB) {
		boolean canPieceMoveThereBasedOnAllItsRules = true;
		boolean legalMoveInput = true;
		int fromRow, fromCol, toRow, toCol;
		
		
		
		do {

			do {
				fromCol = (int) (Math.random() * ((7 - 0) + 1));
				fromRow = (int) (Math.random() * ((7 - 0) + 1));
				legalMoveInput = CB.isValidPieceThere(fromRow, fromCol);
			} while (!legalMoveInput);

			do {
				toCol = (int) (Math.random() * ((7 - 0) + 1));
				toRow = (int) (Math.random() * ((7 - 0) + 1));
			} while ((toCol == fromCol && toRow == fromRow));
			canPieceMoveThereBasedOnAllItsRules = CB.canMoveThere(fromRow, fromCol, toRow, toCol);

		} while (!canPieceMoveThereBasedOnAllItsRules);
//		if (ChessDriver.startCountingTurns)
//			System.out.println("Turns til stalemate : " + (17 - CB.turns++));
		CB.performMove(fromRow, fromCol, toRow, toCol);
	}
	public int countPieces(ChessBoard cb) {
	ArrayList<ArrayList<Piece>> Pieces= cb.getPieces();
	int value= 0;

	int blackOrWhite=(cb.getWhite()&&cb.getTurn()?0:1);
	for(Piece p : Pieces.get(blackOrWhite))
		value+=p.getAIValue();
	
	for(Piece p : Pieces.get(Math.abs(blackOrWhite-1)))
		value-=p.getAIValue();
	
	return value;
	}
	
	
}
