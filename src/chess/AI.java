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
				fromRow = (int) (Math.random() * ((7 - 0) + 1));
				fromCol = (int) (Math.random() * ((7 - 0) + 1));
				legalMoveInput = ChessDriver.isValidPieceThere(fromRow, fromCol);
			} while (!legalMoveInput);

			do {
				toRow = (int) (Math.random() * ((7 - 0) + 1));
				toCol = (int) (Math.random() * ((7 - 0) + 1));
			} while ((toRow == fromRow && toCol == fromCol));
			canPieceMoveThereBasedOnAllItsRules = ChessDriver.canMoveThere(fromRow, fromCol, toRow, toCol);

		} while (!canPieceMoveThereBasedOnAllItsRules);
		if (ChessDriver.startCountingTurns)
			System.out.println("Turns til stalemate : " + (17 - ChessDriver.turns++));
		ChessDriver.performMove(fromRow, fromCol, toRow, toCol);
		ChessDriver.movingPiece = false;
		ChessDriver.fr = fromCol;
		ChessDriver.fc = fromRow;
		ChessDriver.tc = toRow;
		ChessDriver.tr = toCol;
	}
	
	
}
