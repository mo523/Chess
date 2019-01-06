package chess;

import java.util.ArrayList;

public class AI {
	private static ChessBoard CB;

	public static void cpuMovePiece(ChessBoard cb) {
		CB = cb;
		dumbAi();

	}

	public static void dumbAi() {
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

	public void slightlySmarterAi() {
		ChessBoard potentialCB = CB;
		ChessBoard temporaryCB=CB;
		boolean canPieceMoveThereBasedOnAllItsRules = true;
		boolean legalMoveInput = true;
		int fromRow, fromCol, toRow, toCol;
		
		int blackOrWhite = (CB.getWhite() && CB.getTurn() ? 0 : 1);
		for (Piece p : CB.getPieces().get(blackOrWhite))
		{
			for(int m : potentialMoves(p))
			{
				temporaryCB=CB;
				if(temporaryCB.canMoveThere(p.getRow(), p.getCol(), m-(m/10), m/10))
					temporaryCB.performMove(p.getRow(), p.getCol(), m-(m/10), m/10);
				
				if(countPieces(temporaryCB)>countPieces(potentialCB))
					potentialCB=temporaryCB;
				
					
			}
		}
			
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

		while (!canPieceMoveThereBasedOnAllItsRules)
			;
//	if (ChessDriver.startCountingTurns)
//		System.out.println("Turns til stalemate : " + (17 - CB.turns++));
		CB.performMove(fromRow, fromCol, toRow, toCol);

	}

	public int countPieces(ChessBoard cb) {
		ArrayList<ArrayList<Piece>> Pieces = cb.getPieces();
		int value = 0;

		int blackOrWhite = (cb.getWhite() && cb.getTurn() ? 0 : 1);
		for (Piece p : Pieces.get(blackOrWhite))
			value += p.getAIValue();

		for (Piece p : Pieces.get(Math.abs(blackOrWhite - 1)))
			value -= p.getAIValue();

		return value;
	}

	public ArrayList<Integer> potentialMoves(Piece piece) {
		ArrayList<Integer> moves = new ArrayList<Integer>();

		if (piece instanceof Pawn) {
			moves.add(piece.getRow() + (piece.getCol() + 1) * 10);

			if (piece.getRow() < 7)
				moves.add(piece.getRow() + 1 + (piece.getCol() + 1) * 10);

			if (piece.getRow() > 0)
				moves.add(piece.getRow() - 1 + (piece.getCol() + 1) * 10);
		}

		if (piece instanceof Horse) {

			if (piece.getRow() < 6 && piece.getCol() < 7)
				moves.add(piece.getRow() + 2 + (piece.getCol() + 1) * 10);

			if (piece.getRow() < 7 && piece.getCol() < 6)
				moves.add(piece.getRow() + 1 + (piece.getCol() + 2) * 10);

			if (piece.getRow() < 6 && piece.getCol() > 0)
				moves.add(piece.getRow() + 2 + (piece.getCol() - 1) * 10);

			if (piece.getRow() < 7 && piece.getCol() > 1)
				moves.add(piece.getRow() + 1 + (piece.getCol() - 2) * 10);

			if (piece.getRow() > 0 && piece.getCol() > 1)
				moves.add(piece.getRow() - 1 + (piece.getCol() - 2) * 10);

			if (piece.getRow() > 1 && piece.getCol() > 0)
				moves.add(piece.getRow() - 2 + (piece.getCol() - 1) * 10);

			if (piece.getRow() > 0 && piece.getCol() < 7)
				moves.add(piece.getRow() - 2 + (piece.getCol() + 1) * 10);

			if (piece.getRow() > 1 && piece.getCol() < 6)
				moves.add(piece.getRow() - 1 + (piece.getCol() + 2) * 10);

		}

		if (piece instanceof Rook) {

			for (int i = piece.getRow(); i < 8; i++) {
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}
			for (int i = piece.getRow(); i >= 0; i--) {
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}

			for (int i = piece.getCol(); i < 8; i++) {
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}

			for (int i = piece.getCol(); i >= 0; i--) {
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}

		}

		if (piece instanceof Bishop) {
			for (int i = 0; i < 8; i++) {
				if (piece.getRow() + i > 7 || piece.getRow() + i > 7)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() + i > 7 || piece.getRow() - i < 0)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() - i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getRow() + i > 7)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getRow() + -i < 0)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() - i))
					break;
			}
		}

		if (piece instanceof Queen) {

			for (int i = piece.getRow(); i < 8; i++) {
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}
			for (int i = piece.getRow(); i >= 0; i--) {
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}

			for (int i = piece.getCol(); i < 8; i++) {
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}

			for (int i = piece.getCol(); i >= 0; i--) {
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}
			for (int i = 0; i < 8; i++) {
				if (piece.getRow() + i > 7 || piece.getRow() + i > 7)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() + i > 7 || piece.getRow() - i < 0)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() - i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getRow() + i > 7)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getRow() + -i < 0)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() - i))
					break;
			}
		}

		if (piece instanceof King) {

			if (piece.getRow() < 7) {
				moves.add(piece.getRow() + 1 + piece.getCol() * 10);

				if (piece.getCol() < 7)
					moves.add(piece.getRow() + 1 + (piece.getCol() + 1 * 10));

				if (piece.getCol() > 0)
					moves.add(piece.getRow() + 1 + (piece.getCol() - 1 * 10));

			}

			if (piece.getRow() > 0) {
				moves.add(piece.getRow() - 1 + piece.getCol() * 10);

				if (piece.getCol() < 7)
					moves.add(piece.getRow() - 1 + (piece.getCol() + 1 * 10));

				if (piece.getCol() > 0)
					moves.add(piece.getRow() - 1 + (piece.getCol() - 1 * 10));

			}

			if (piece.getCol() < 7)
				moves.add(piece.getRow() + (piece.getCol() + 1 * 10));

			if (piece.getCol() > 0)
				moves.add(piece.getRow() + (piece.getCol() - 1 * 10));

		}

		return moves;
	}

}
