package chess;
 
public class AI
{
	public static void cpuMovePiece() {
		boolean canPieceMoveThereBasedOnAllItsRules = true;
		boolean legalMoveInput = true;
		int fromRow, fromCol, toRow, toCol;
		ChessDriver.movingPiece = true;
		do {

			do {
				fromCol = (int) (Math.random() * ((7 - 0) + 1));
				fromRow = (int) (Math.random() * ((7 - 0) + 1));
				legalMoveInput = ChessDriver.isValidPieceThere(fromRow, fromCol);
			} while (!legalMoveInput);

			do {
				toCol = (int) (Math.random() * ((7 - 0) + 1));
				toRow = (int) (Math.random() * ((7 - 0) + 1));
			} while ((toCol == fromCol && toRow == fromRow));
			canPieceMoveThereBasedOnAllItsRules = ChessDriver.canMoveThere(fromRow, fromRow, toRow, toCol);

		} while (!canPieceMoveThereBasedOnAllItsRules);
		if (ChessDriver.startCountingTurns)
			System.out.println("Turns til stalemate : " + (17 - ChessDriver.turns++));
		ChessDriver.performMove(fromRow, fromRow, toRow, toCol);
		ChessDriver.movingPiece = false;
		ChessDriver.fr = fromRow;
		ChessDriver.fc = fromCol;
		ChessDriver.tc = toCol;
		ChessDriver.tr = toRow;
	}
	
	public int pawnMove() {
		return 0;
	}
	
	
}
