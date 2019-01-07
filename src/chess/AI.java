package chess;

import java.util.ArrayList;
import java.util.Scanner;

public class AI {
	private static ChessBoard CB;
	private static Scanner kybd = new Scanner(System.in);
	private static boolean quit = false;
	private static boolean lvl_2 = false;

	public static void cpuMovePiece(ChessBoard cb) {
		CB = cb;
		menu();

	}

	public static void playGame() {

		do {
			if (!quit && !CB.getTurn() && !lvl_2)
				dumbAi();
			else if (!quit && !CB.getTurn() && lvl_2)
				slightlySmarterAi();
			else if (movePiece())
				break;
			CB.displayChoice();
		} while (CB.gameStatus() || !quit);
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

	public static void slightlySmarterAi() {

		Piece[][] board = CB.getBoard();
		int tempValue = 0;

		int tempMoveToRow = 0;
		int tempMoveFromCol = 0;
		int tempMoveToCol = 0;
		int tempMoveFromRow = 0;

		int blackOrWhite = (CB.getWhite() && !CB.getTurn() ? 0 : 1);
		for (Piece p : CB.getPieces().get(blackOrWhite)) {
			for (int m : potentialMoves(p)) {

				if (CB.canMoveThere(p.getRow(), p.getCol(), m % 10, m / 10)) {
					System.out.print(
							p.getClass() + " " + p.getRow() + " " + p.getCol() + ":" + m % 10 + " " + m / 10 + " \n");

					if (board[m % 10][m / 10] != null)
						if (tempValue < board[m % 10][m / 10].getAIValue()) {
							tempValue = board[m % 10][m / 10].getAIValue();
							tempMoveToRow = m % 10;
							tempMoveToCol = m / 10;
							tempMoveFromRow = p.getRow();
							tempMoveFromCol = p.getCol();
						}

				}

			}

		}

		if (tempValue > 0) {
			CB.performMove(tempMoveFromRow, tempMoveFromCol, tempMoveToRow, tempMoveToCol);
		} else
			dumbAi();

	}

	public static int countPieces(ChessBoard cb) {
		ArrayList<ArrayList<Piece>> Pieces = cb.getPieces();
		int value = 0;

		int blackOrWhite = (cb.getWhite() && cb.getTurn() ? 0 : 1);
		for (Piece p : Pieces.get(blackOrWhite))
			value += p.getAIValue();

		for (Piece p : Pieces.get(Math.abs(blackOrWhite - 1)))
			value -= p.getAIValue();

		return value;
	}

	public static int advancedCountPieces(Piece[][] pieces, boolean white) {
		int value = 0;
		for (int i = 0; i > 8; i++)
			for (int i2 = 0; i2 > 8; i2++)
				if (pieces[i][i2] != null)
					if (pieces[i][i2].isWhite())
						value += pieces[i][i2].getAIValue();
					else
						value -= pieces[i][i2].getAIValue();

		if (!white)
			value *= -1;

		return value;

	}

	public static ArrayList<Integer> potentialMoves(Piece piece) {
		ArrayList<Integer> moves = new ArrayList<Integer>();

		if (piece instanceof Pawn) {

			if (piece.getRow() < 7)
				moves.add(piece.getRow() + 1 + (piece.getCol()) * 10);

			if (piece.getRow() < 7 && piece.getCol() < 7)
				moves.add(piece.getRow() + 1 + (piece.getCol() + 1) * 10);

			if (piece.getRow() < 7 && piece.getCol() > 0)
				moves.add(piece.getRow() + 1 + (piece.getCol() - 1) * 10);
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
				if (piece.getRow() + i > 7 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() + i > 7 || piece.getCol() - i < 0)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() - i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getCol() - i < 0)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() - i) * 10);

				if (piece.getRow() - i >= 0 && piece.getCol() - i >= 0)
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
				if (piece.getRow() + i > 7 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() + i > 7 || piece.getCol() - i < 0)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() - i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++) {
				if (piece.getRow() - i < 0 || piece.getCol() - i < 0)
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

	public static boolean movePiece() {
		String name = CB.getWhite() ? "White" : "Black";
		boolean canMoveThere = true;
		String move;
		boolean legalMoveInput = true;
		int fromRow, fromCol, toRow, toCol;

		do {
			if (!canMoveThere)
				System.out.println("Can't Move There");
			System.out.println("\n" + name + ", Which piece would you like to move?\nType 'm' for menu");
			do {
				if (!legalMoveInput)
					System.out.println("\nYou do not have a piece there, try again.");
				move = getPosition();
				if (move.equals("m"))
					return true;
				fromCol = move.charAt(0) - 97;
				fromRow = move.charAt(1) - 49;
				legalMoveInput = CB.isValidPieceThere(fromRow, fromCol);
			} while (!legalMoveInput);
			System.out.println("\nWhere would you like to move your " + CB.getName(fromRow, fromCol) + " to?");
			do {
				move = getPosition();
				if (move.equals("m"))
					return true;
				toCol = move.charAt(0) - 97;
				toRow = move.charAt(1) - 49;
				if (toCol == fromCol && toRow == fromRow)
					System.out.println("\nCan't move to same place, try again.");
			} while ((toCol == fromCol && toRow == fromRow));
			canMoveThere = CB.canMoveThere(fromRow, fromCol, toRow, toCol);
		} while (!canMoveThere);

		boolean promote = CB.performMove(fromRow, fromCol, toRow, toCol);
		if (promote)
			promote(toRow, toCol);

		return false;
	}

	public static String getPosition() {
		String pos;
		boolean badInput = false;
		do {
			badInput = true;
			pos = kybd.next().toLowerCase();
			if (pos.length() != 2)
				System.out.println("\nPosition must be 2 characters, try again.");
			else if (pos.charAt(0) < 97 || pos.charAt(0) > 104 || pos.charAt(1) < 49 || pos.charAt(1) > 56)
				System.out.println("\nInvalid position entry. It must be a [a-h][1-8], try again.");
			else
				badInput = false;
		} while (badInput);
		return pos;
	}

	public static void menu() {
		System.out.println("0 to quit\n1 to Start new Computer Lvl 1\n2To Play Computer lvl 2\3 to go back");
		int choice = kybd.nextInt();

		switch (choice) {
		case 0:
			quit = true;
			break;

		case 1:
			playGame();
			break;

		case 2:
			lvl_2 = true;
			playGame();
			break;

		default:
			break;

		}
	}

	public static void promote(int row, int col) {
		CB.displayChoice();
		int choice;
		do {
			System.out.println("\nWhat would you like to convert your pawn to?");
			System.out.println("1. Queen\n2. Bishop\n3. Rook\n4. Horse");
			choice = kybd.nextInt();
			if (choice < 1 || choice > 4)
				System.out.println("Not a valid choice, 1-4");
		} while (choice < 1 || choice > 4);
		CB.promotion(row, col, choice);
	}
}
