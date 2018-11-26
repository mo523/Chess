
public class AI
{
	public static void cpuMovePiece() {
		boolean canPieceMoveThereBasedOnAllItsRules = true;
		boolean legalMoveInput = true;
		int fromCol, fromRow, toCol, toRow;
		ChessDriver.movingPiece = true;
		do {

			do {
				fromCol = (int) (Math.random() * ((7 - 0) + 1));
				fromRow = (int) (Math.random() * ((7 - 0) + 1));
				legalMoveInput = ChessDriver.isValidPieceThere(fromCol, fromRow);
			} while (!legalMoveInput);

			do {
				toCol = (int) (Math.random() * ((7 - 0) + 1));
				toRow = (int) (Math.random() * ((7 - 0) + 1));
			} while ((toCol == fromCol && toRow == fromRow));
			canPieceMoveThereBasedOnAllItsRules = ChessDriver.canMoveThere(fromCol, fromRow, toCol, toRow);

		} while (!canPieceMoveThereBasedOnAllItsRules);
		if (ChessDriver.startCountingTurns)
			System.out.println("Turns til stalemate : " + (17 - ChessDriver.turns++));
		ChessDriver.performMove(fromCol, fromRow, toCol, toRow);
		ChessDriver.movingPiece = false;
		ChessDriver.fr = fromRow;
		ChessDriver.fc = fromCol;
		ChessDriver.tc = toCol;
		ChessDriver.tr = toRow;
	}
}
