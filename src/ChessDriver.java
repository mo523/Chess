
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

public class ChessDriver {
	private static Scanner kyb = new Scanner(System.in);
	private static final boolean IS_WHITE = true;// this is to make what color the pieces are more clear
	private static final boolean IS_BLACK = false;// this is to make what color the pieces are more clear
	private static boolean debug;
	private static Piece whiteKing, blackKing;
	private static boolean whitesTurn = true;
	private static Piece[][] chessBoard = new Piece[8][8];
	private static boolean cpuGame;
	private static boolean cpuTurn;
	static boolean startCountingTurns;
	static int turns = 0;
	private static String errorMessage;
	static boolean movingPiece = false;
	static int fr = -1, fc = -1, tr = -1, tc = -1;
	private static boolean useJansi = !System.getProperty("user.name").equalsIgnoreCase("moshe");
	private static boolean networkGame = false;
	private static Network net;
	private static boolean localTurn;
	// System.getProperty("os.name").contains("Windows") &&
	// !System.getProperty("os.name").contains("10");
	// private HashMap<String,Piece> pieces = new HashMap<>();

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		if (useJansi)
			AnsiConsole.systemInstall(); // MUST BE ON TOP - PARSES ALL THE UNICODE
		Display.clear();
		setUpPieces();
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
		int fromCol, fromRow, toCol, toRow;
		movingPiece = true;

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
				legalMoveInput = isValidPieceThere(fromCol, fromRow);
			} while (!legalMoveInput);

			System.out.println("\nWhere would you like to move your " + chessBoard[fromRow][fromCol].name + " to?");
			do {
				move = getPosition();
				if (move.equals("m"))
					return true;
				toCol = 104 - move.charAt(0);
				toRow = move.charAt(1) - 49;
				if (toCol == fromCol && toRow == fromRow)
					System.out.println("\nCan't move to same place, try again.");
			} while ((toCol == fromCol && toRow == fromRow));
			canMoveThere = canMoveThere(fromCol, fromRow, toCol, toRow);
		} while (!canMoveThere);

		fc = fromCol;
		fr = fromRow;
		tc = toCol;
		tr = toRow;
		performMove(fromCol, fromRow, toCol, toRow);
		if (chessBoard[toRow][toCol] instanceof Pawn && ((whitesTurn && toRow == 7) || (!whitesTurn && toRow == 0)))
			pawnReachedOtherSide(toRow, toCol);
		// if pawn enPassantAble it kills
		if ((whitesTurn && toRow == 5 && chessBoard[4][toCol] != null
				&& chessBoard[4][toCol].isInstanceOf().equals("Pawn")
				&& ((Pawn) chessBoard[4][toCol]).isEnPassantAble()))
			chessBoard[4][toCol] = null;
		if (!whitesTurn && toRow == 2 && chessBoard[3][toCol] != null
				&& chessBoard[3][toCol].isInstanceOf().equals("Pawn")
				&& ((Pawn) chessBoard[3][toCol]).isEnPassantAble())
			chessBoard[3][toCol] = null;

		// makes a piece enPassantAble
		if (chessBoard[toRow][toCol].isInstanceOf().equals("Pawn")) {
			if (Math.abs(toRow - fromRow) == 2)
				((Pawn) chessBoard[toRow][toCol]).changeEnPassantAble(true);
			else
				((Pawn) chessBoard[toRow][toCol]).changeEnPassantAble(false);
		}
		movingPiece = false;
		return false;
	}

	public static boolean canMoveThere(int fromCol, int fromRow, int toCol, int toRow) {
		return chessBoard[fromRow][fromCol].isLegalMove(fromCol, fromRow, toCol, toRow, chessBoard,
				(whitesTurn ? whiteKing : blackKing));
	}

	public static boolean isValidPieceThere(int col, int row) {
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
					"\n\nMain Menu\n\n0. Quit\n1. New Game (Two Player) \n2. New game (One Player)\n3. New networked game\n4. Open saved game\n\n5. 1 with debug\n6. 2 with debug\n7. 3 with debug");
			do {
				choice = kyb.nextInt();
			} while (choice < 0 && choice > 3);
			switch (choice) {
			case 0:
				break;
			case 5:
				debug = true;
			case 1:
				playGame();
				break;
			case 6:
				debug = true;
			case 2:
				System.out.println("(B)lack or (W)hite?");
				cpuTurn = kyb.next().toUpperCase().equals("B") ? true : false;
				cpuGame = true;
				playGame();
				break;
			case 7:
				debug = true;
			case 3:
				networkGame = true;
				networkedGame();
				break;
			case 4:
				SaveGameFunctionality.loadSavedGame();
				break;
			}
			System.out.println();
		} while (choice != 0);
	}

	public static boolean menu() throws FileNotFoundException, ClassNotFoundException, IOException {
		System.out.println("\n\nMenu\n0. Main menu\n1. Save game\n2. Change debug mode\n3. Change gameplay mode");
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
			net = new Network();
		} else {
			System.out.println("IP Address?");
			kyb.nextLine();
			String ip = kyb.nextLine();
			if (ip.equals("0"))
				ip = "127.0.0.1";
			net = new Network(ip);
		}
		localTurn = net.isServer();
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
			if (net.getClientData() == null)
				throw new IOException();
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
			if (choice > 4 || choice < 1)
				System.out.println("Not a valid choice, 1-4");
		} while (choice > 4 || choice < 1);

		chessBoard[row][col] = choice == 1 ? new Queen(whitesTurn)
				: choice == 2 ? new Bishop(whitesTurn) : choice == 3 ? new Rook(whitesTurn) : new Horse(whitesTurn);
	}

	public static boolean isInCheck() {
		return whitesTurn ? whiteKing.inCheck(whiteKing, chessBoard) : blackKing.inCheck(blackKing, chessBoard);
	}

	public static boolean notInCheckMate() {
		if (!isInCheck())
			return true;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (chessBoard[i][j] != null && chessBoard[i][j].isWhite() == whitesTurn)
					for (int x = 0; x < 8; x++)
						for (int y = 0; y < 8; y++)
							if ((whitesTurn ? whiteKing.notInCheckmate(j, i, y, x, chessBoard, whiteKing)
									: blackKing.notInCheckmate(j, i, y, x, chessBoard, blackKing)))
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
		int count = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (chessBoard[i][j] != null && chessBoard[i][j].isWhite() == white)
					count++;
		return count == 1;
	}

	public static boolean eighteenMoveStalemate() {
		if (!(startCountingTurns) && (oneKing(true) || oneKing(false)))
			startCountingTurns = true;
		return turns < 18;
	}

	public static boolean canMove() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (chessBoard[i][j] != null && chessBoard[i][j].isWhite() == whitesTurn)
					for (int x = 0; x < 8; x++)
						for (int y = 0; y < 8; y++)
							if (chessBoard[i][j].isLegalMove(j, i, x, y, chessBoard,
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

	public static boolean isMovingPiece() {
		return movingPiece;
	}

	public static void performMove(int fromCol, int fromRow, int toCol, int toRow) {
		chessBoard[toRow][toCol] = chessBoard[fromRow][fromCol];
		chessBoard[fromRow][fromCol] = null;
	}

	public static void setUpPieces() {
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

		chessBoard[1][0] = new Pawn(IS_WHITE);
		chessBoard[1][1] = new Pawn(IS_WHITE);
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

	public static void setMovingPiece(boolean movingPiece) {
		ChessDriver.movingPiece = movingPiece;
	}
}