package chess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;

public class ChessDriver {
	private static Scanner kyb = new Scanner(System.in);
	private static boolean debug;
	private static Piece whiteKing, blackKing;
	private static boolean whitesTurn;
	private static Piece[][] chessBoard;
	private static ArrayList<ArrayList<Piece>> pieces;
	private static boolean cpuGame;
	private static boolean cpuTurn;
	static boolean startCountingTurns;
	static int turns = 0;
	private static String errorMessage;
	static int fr = -1, fc = -1, tr = -1, tc = -1;
	private static boolean useJansi = !System.getProperty("user.name").equalsIgnoreCase("moshe");
	private static boolean networkGame = false;
	private static Network net;
	private static boolean localTurn;

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		if (useJansi)
			AnsiConsole.systemInstall(); // MUST BE ON TOP - PARSES ALL THE UNICODE
		Display.clear();
		System.out.println("Welcome to chess!");
		initialMenu();
		kyb.close();
		if (useJansi)
			AnsiConsole.systemUninstall();
	}

	public static void playGame() throws FileNotFoundException, IOException, ClassNotFoundException {
		do {
			if (cpuGame && cpuTurn)
				AI.cpuMovePiece();
			else if (networkGame)
				netMove();
			else {
				displayChoice();
				if (movePiece())
					break;
			}
			localTurn = !localTurn;
			whitesTurn = !whitesTurn;
			cpuTurn = !cpuTurn;
		} while (notInCheckMate() && notInStaleMate());
		if (!notInCheckMate() || !notInStaleMate())
			endGame();
	}

	public static void endGame() {
		displayChoice();
		if (!notInCheckMate())
			System.out.println("Sorry " + (whitesTurn ? "White" : "Black") + ". Checkmate, you lose.");
		else
			System.out.println("Stalemate.");
	}

	public static boolean movePiece() throws FileNotFoundException, IOException, ClassNotFoundException {
		String name = whitesTurn ? "White" : "Black";
		boolean canMoveThere = true;
		String move;
		boolean legalMoveInput = true;
		int fromRow, fromCol, toRow, toCol;

		do {
			if (!canMoveThere)
				System.out.println(errorMessage);
			System.out.println("\n" + name + ", Which piece would you like to move?\nType 'm' for menu");
			do {
				if (!legalMoveInput)
					System.out.println("\nYou do not have a piece there, try again.");
				move = getPosition();
				if (move.equals("m"))
					return true;
				fromCol = 104 - move.charAt(0);
				fromRow = move.charAt(1) - 49;
				legalMoveInput = isValidPieceThere(fromRow, fromCol);
			} while (!legalMoveInput);
			String movingPiece = chessBoard[fromRow][fromCol].toString();
			System.out.println("\nWhere would you like to move your " + movingPiece.substring(6, movingPiece.length() -9) + " to?");
			do {
				move = getPosition();
				if (move.equals("m"))
					return true;
				toCol = 104 - move.charAt(0);
				toRow = move.charAt(1) - 49;
				if (toCol == fromCol && toRow == fromRow)
					System.out.println("\nCan't move to same place, try again.");
			} while ((toCol == fromCol && toRow == fromRow));
			canMoveThere = canMoveThere(fromRow, fromCol, toRow, toCol);
		} while (!canMoveThere);

		fc = fromCol;
		fr = fromRow;
		tc = toCol;
		tr = toRow;
		performMove(fromRow, fromCol, toRow, toCol);
		if (chessBoard[toRow][toCol] instanceof Pawn && ((whitesTurn && toRow == 7) || (!whitesTurn && toRow == 0)))
			pawnReachedOtherSide(toRow, toCol);
		// if pawn enPassantAble it kills
		System.out.println("a");
		if (chessBoard[toRow][toCol] instanceof Pawn)
			((Pawn) chessBoard[toRow][toCol]).moved();
		if (chessBoard[toRow][toCol] instanceof Pawn)
			if (((Pawn) chessBoard[toRow][toCol]).enPassantMove()) {
				if (whitesTurn)
					chessBoard[4][toCol] = null;

				if (!whitesTurn)
					chessBoard[3][toCol] = null;
			}

		// makes a piece enPassantAble
		if (chessBoard[toRow][toCol] instanceof Pawn) {
			if (Math.abs(toRow - fromRow) == 2)
				((Pawn) chessBoard[toRow][toCol]).changeEnPassantAble(true);
			else
				((Pawn) chessBoard[toRow][toCol]).changeEnPassantAble(false);
		}
		return false;
	}

	public static boolean canMoveThere(int fromRow, int fromCol, int toRow, int toCol) {
		return chessBoard[fromRow][fromCol].isLegalMove(fromRow, fromCol, toRow, toCol, chessBoard,
				(whitesTurn ? whiteKing : blackKing));
	}

	public static boolean isValidPieceThere(int row, int col) {
		return !(chessBoard[row][col] == null || chessBoard[row][col].white != whitesTurn);
	}

	public static String getPosition() throws FileNotFoundException, IOException, ClassNotFoundException {
		String pos;
		boolean badInput = false;
		do {
			badInput = true;
			pos = kyb.next().toLowerCase();
			if (pos.equalsIgnoreCase("m")) {
				if (menu())
					return pos;
			} else if (pos.length() != 2)
				System.out.println("\nPosition must be 2 characters, try again.");
			else if (pos.charAt(0) < 97 || pos.charAt(0) > 104 || pos.charAt(1) < 49 || pos.charAt(1) > 56)
				System.out.println("\nInvalid position entry. It must be a [a-h][1-8], try again.");
			else
				badInput = false;
		} while (badInput);
		return pos;
	}

	public static void initialMenu() throws FileNotFoundException, ClassNotFoundException, IOException {
		int choice;
		do {
			System.out.println(
					"\n\nMain Menu\n\n0. Quit\n1. New Game (Two Player) \n2. New game (One Player)\n3. New networked game\n4. Open saved game\n5. Continue Game\n\n6. 1 with debug\n7. 2 with debug\n8. 3 with debug");
			do {
				choice = kyb.nextInt();
			} while (choice < 0 || choice > 8);
			switch (choice) {
			case 0:
				break;
			case 6:
				debug = true;
			case 1:
				setUpPieces();
				playGame();
				break;
			case 7:
				debug = true;
			case 2:
				System.out.println("(B)lack or (W)hite?");
				cpuTurn = kyb.next().toUpperCase().equals("B") ? true : false;
				cpuGame = true;
				setUpPieces();
				playGame();
				break;
			case 8:
				debug = true;
			case 3:
				networkGame = true;
				setUpPieces();
				networkedGame();
				break;
			case 4:
				SavedGame s = SaveGameFunctionality.loadSavedGame();
				setGame(s);
				break;
			case 5:
				playGame();
				break;
			}
			System.out.println();
		} while (choice != 0);
	}

	public static boolean menu() throws FileNotFoundException, ClassNotFoundException, IOException {
		System.out.println("\n\nMenu\n0. Main menu\n1. Save game\n2. Change debug mode\n3. Change gameplay mode\n4. Continue game");
		int choice = kyb.nextInt();
		switch (choice) {
		case 0:
			return true;
		case 1:
			SaveGameFunctionality.saveGame(chessBoard, debug, whiteKing, blackKing, whitesTurn, cpuGame, cpuTurn,
					startCountingTurns, turns);
			break;
		case 2:
			debug = !debug;
			break;
		case 3:
			cpuGame = !cpuGame;
			break;
		case 4:
			break;
		}
		return false;
	}

	public static void networkedGame() throws IOException, ClassNotFoundException {
		System.out.println("Would you like to:\n1. Host a game\n2. Join a game");
		int choice;
		do {
			choice = kyb.nextInt();
		} while (choice != 1 && choice != 2);
		if (choice == 1) {
			System.out.println("What would you like to call your game?");
			kyb.nextLine();
			net = new Network(kyb.nextLine());
		} else {
			net = new Network("");
			System.out.println(net.getGames());
			net.join(kyb.nextInt());
		}
		localTurn = net.isHost();
		try {
			playGame();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			networkGame = false;
			net.close();
		}
	}

	public static boolean netMove() throws IOException, ClassNotFoundException {
		if (localTurn) {
			displayChoice();
			if (movePiece())
				return true;
			net.sendServerData(fr, fc, tr, tc);
		} else {
			displayChoice();
			System.out.println("\nWaiting for other players move");
			int[] data = net.getClientData();
			performMove(data[1], data[0], data[3], data[2]);
			fr = data[0];
			fc = data[1];
			tr = data[2];
			tc = data[3];
		}
		return false;
	}

	public static void pawnReachedOtherSide(int row, int col) {
		displayChoice();
		int choice;
		do {
			System.out.println("\nWhat would you like to convert your pawn to?");
			System.out.println("1. Queen\n2. Bishop\n3. Rook\n4. Horse");
			choice = kyb.nextInt();
			if (choice < 1 || choice > 4)
				System.out.println("Not a valid choice, 1-4");
		} while (choice < 1 || choice > 4);

		chessBoard[row][col] = choice == 1 ? new Queen(whitesTurn, row, col)
				: choice == 2 ? new Bishop(whitesTurn, row, col)
						: choice == 3 ? new Rook(whitesTurn, row, col) : new Horse(whitesTurn, row, col);
	}

	public static boolean isInCheck() {
		return whitesTurn ? whiteKing.inCheck(whiteKing, chessBoard) : blackKing.inCheck(blackKing, chessBoard);
	}

	public static boolean notInCheckMate() {
		if (!isInCheck())
			return true;
		for (Piece piece : pieces.get(whitesTurn ? 0 : 1))
			for (int i = 7; i < 8; i++)
				for (int j = 2; j < 8; j++)
					if (whitesTurn
							? whiteKing.notInCheckmate(piece.getRow(), piece.getCol(), i, j, chessBoard, whiteKing)
							: blackKing.notInCheckmate(piece.getRow(), piece.getCol(), i, j, chessBoard, blackKing))
						return true;
		return false;
	}

	public static boolean notInStaleMate() {
		boolean white = oneKing(true);
		boolean black = oneKing(false);
		if (white && black)
			return false;
		if (white || black)
			return eighteenMoveStalemate();
		return canMove();
	}

	public static boolean oneKing(boolean white) {
		return pieces.get(white ? 0 : 1).size() == 1;
	}

	public static boolean eighteenMoveStalemate() {
		if (!(startCountingTurns) && (oneKing(true) || oneKing(false)))
			startCountingTurns = true;
		return turns < 18;
	}

	public static boolean canMove() {
		for (Piece piece : pieces.get(whitesTurn ? 0 : 1))
			for (int toRow = 0; toRow < 8; toRow++)
				for (int toCol = 0; toCol < 8; toCol++)
					if (piece.isLegalMove(piece.getRow(), piece.getCol(), toRow, toCol, chessBoard,
							whitesTurn ? whiteKing : blackKing))
						return true;
		return false;
	}

	public static void displayChoice() {
		if (debug)
			Display.debug(chessBoard, whitesTurn, useJansi, fr, fc, tr, tc);
		else
			Display.display(whitesTurn, useJansi, chessBoard, fr, fc, tr, tc);
		if (startCountingTurns)
			System.out.println("Turns til stalemate : " + (17 - turns++));
		if (isInCheck())
			System.out.println("\nWarning! Your king is in check!\n");
	}

	public static void performMove(int fromRow, int fromCol, int toRow, int toCol) {
		chessBoard[fromRow][fromCol].setRow(toRow);
		chessBoard[fromRow][fromCol].setCol(toCol);
		pieces.get(whitesTurn ? 1 : 0).remove(chessBoard[toRow][toCol]);
		chessBoard[toRow][toCol] = chessBoard[fromRow][fromCol];
		chessBoard[fromRow][fromCol] = null;
	}

	public static void setUpPieces() {
		final boolean IS_WHITE = true;// this is to make what color the pieces are more clear
		final boolean IS_BLACK = false;// this is to make what color the pieces are more clear
		chessBoard = new Piece[8][8];
		pieces = new ArrayList<>();
		pieces.add(new ArrayList<Piece>());
		pieces.add(new ArrayList<Piece>());
		whitesTurn = true;
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

		chessBoard[1][0] = new Pawn(IS_WHITE, 1, 0);
		chessBoard[1][1] = new Pawn(IS_WHITE, 1, 1);
		chessBoard[1][2] = new Pawn(IS_WHITE, 1, 2);
		chessBoard[1][3] = new Pawn(IS_WHITE, 1, 3);
		chessBoard[1][4] = new Pawn(IS_WHITE, 1, 4);
		chessBoard[1][5] = new Pawn(IS_WHITE, 1, 5);
		chessBoard[1][6] = new Pawn(IS_WHITE, 1, 6);
		chessBoard[1][7] = new Pawn(IS_WHITE, 1, 7);
		chessBoard[6][0] = new Pawn(IS_BLACK, 6, 0);
		chessBoard[6][1] = new Pawn(IS_BLACK, 6, 1);
		chessBoard[6][2] = new Pawn(IS_BLACK, 6, 2);
		chessBoard[6][3] = new Pawn(IS_BLACK, 6, 3);
		chessBoard[6][4] = new Pawn(IS_BLACK, 6, 4);
		chessBoard[6][5] = new Pawn(IS_BLACK, 6, 5);
		chessBoard[6][6] = new Pawn(IS_BLACK, 6, 6);
		chessBoard[6][7] = new Pawn(IS_BLACK, 6, 7);
		chessBoard[0][0] = new Rook(IS_WHITE, 0, 0);
		chessBoard[0][1] = new Horse(IS_WHITE, 0, 1);
		chessBoard[0][2] = new Bishop(IS_WHITE, 0, 2);
		chessBoard[0][3] = new King(IS_WHITE, 0, 3);
		chessBoard[0][4] = new Queen(IS_WHITE, 0, 4);
		chessBoard[0][5] = new Bishop(IS_WHITE, 0, 5);
		chessBoard[0][6] = new Horse(IS_WHITE, 0, 6);
		chessBoard[0][7] = new Rook(IS_WHITE, 0, 7);
		chessBoard[7][0] = new Rook(IS_BLACK, 7, 0);
		chessBoard[7][1] = new Horse(IS_BLACK, 7, 1);
		chessBoard[7][2] = new Bishop(IS_BLACK, 7, 2);
		chessBoard[7][3] = new King(IS_BLACK, 7, 3);
		chessBoard[7][4] = new Queen(IS_BLACK, 7, 4);
		chessBoard[7][5] = new Bishop(IS_BLACK, 7, 5);
		chessBoard[7][6] = new Horse(IS_BLACK, 7, 6);
		chessBoard[7][7] = new Rook(IS_BLACK, 7, 7);
		whiteKing = chessBoard[0][3];
		blackKing = chessBoard[7][3];
//		chessBoard[0][0] = new Rook(true,0,0);
//		chessBoard[0][1] = new Horse(true,0,1);
//		chessBoard[0][2] = new Bishop(true,0,2);
//		chessBoard[0][3] = new King(true,0,3);
//		chessBoard[0][5] = new Bishop(true,0,5);
//		chessBoard[0][6] = new Horse(true,0,6);
//		chessBoard[0][7] = new Rook(true,0,7);
//		chessBoard[1][0] = new Pawn(true,1,0);
//		chessBoard[1][1] = new Pawn(true,1,1);
//		chessBoard[1][2] = new Pawn(true,1,2);
//		chessBoard[1][4] = new Pawn(true,1,4);
//		chessBoard[1][5] = new Pawn(true,1,5);
//		chessBoard[3][3] = new Pawn(true,3,3);
//		chessBoard[5][5] = new Queen(true,5,5);
//		chessBoard[6][7] = new Queen(true,6,7);
//		chessBoard[6][2] = new King(false,6,2);
		blackKing = chessBoard[6][2];
		whiteKing = chessBoard[0][3];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				if (chessBoard[i][j] != null)
					if (chessBoard[i][j].isWhite())
						pieces.get(0).add(chessBoard[i][j]);
					else if (!chessBoard[i][j].isWhite())
						pieces.get(1).add(chessBoard[i][j]);
			}
		// // debug for checkmate
		// chessBoard[6][4] = chessBoard[0][3];
		// chessBoard[6][5] = chessBoard[0][5];
		// chessBoard[6][3] = chessBoard[0][0];
		// chessBoard[0][0] = null;
		// chessBoard[0][5] = null;
		// chessBoard[2][4] = null;
	}

	public static void setGame(SavedGame s) {
		debug = s.isDebug();
		whiteKing = s.getWhiteKing();
		blackKing = s.getBlackKing();
		whitesTurn = s.isWhitesTurn();
		chessBoard = s.getChessBoard();
		cpuGame = s.isCpuGame();
		cpuTurn = s.isCpuTurn();
		startCountingTurns = s.isStartCountingTurns();
		turns = s.getTurns();
	}

	public static void setErrorMessage(String errorMessage) {
		ChessDriver.errorMessage = errorMessage;
	}

	// needed for junit
	public static void setChessBoard(Piece[][] chessBoard) {
		ChessDriver.chessBoard = chessBoard;
	}
	public static ArrayList<ArrayList<Piece>> getPieces()
	{
		return pieces;
	}
}