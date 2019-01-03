package chess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;

public class ChessDriver {
	private static Scanner kyb = new Scanner(System.in);

	private static ChessBoard CB;
	private static String errorMessage;
	private static boolean useJansi = !System.getProperty("user.name").equalsIgnoreCase("moshe");
	private static Network net;

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

	public static void initialMenu() throws FileNotFoundException, ClassNotFoundException, IOException {
		int choice;
		boolean debug = false;
		boolean cpuGame = false;
		boolean cpuTurn = false;
		boolean networkGame = false;
		SavedGame s = null;
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
				break;
			case 7:
				debug = true;
			case 2:
				System.out.println("(B)lack or (W)hite?");
				cpuTurn = kyb.next().toUpperCase().equals("B") ? true : false;
				cpuGame = true;
				break;
			case 8:
				debug = true;
			case 3:
				networkGame = true;
				break;
			case 4:
				s = SaveGameFunctionality.loadSavedGame();
				break;
			default: // 0, 1, 5
				break;
			}
			CB = new ChessBoard(debug, cpuGame, cpuTurn, networkGame);
			if (choice != 0) {
				if (s != null)
					CB.loadGame(s);
				System.out.println();
				playGame();
			}
		} while (choice != 0);
	}

	public static void playGame() throws FileNotFoundException, IOException, ClassNotFoundException {
		boolean gameOver = false;
		do {
			if (CB.getCpuGame() && CB.getCpuTurn())
				AI.cpuMovePiece(CB);
			else if (CB.getNetGame())
				netMove();
			else {
				CB.displayChoice();
				if (movePiece())
					break;
			}
			gameOver = CB.moved();
		} while (!gameOver);
		if (gameOver)
			endGame();
	}

	public static void endGame() {
		CB.displayChoice();
		if (CB.getEnd())
			System.out.println("Sorry " + (CB.getWhite() ? "White" : "Black") + ". Checkmate, you lose.");
		else
			System.out.println("Stalemate.");
	}

	public static boolean movePiece() throws FileNotFoundException, IOException, ClassNotFoundException {
		String name = CB.getWhite() ? "White" : "Black";
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
				legalMoveInput = CB.isValidPieceThere(fromRow, fromCol);
			} while (!legalMoveInput);
			System.out.println("\nWhere would you like to move your " + CB.getName(fromRow, fromCol) + " to?");
			do {
				move = getPosition();
				if (move.equals("m"))
					return true;
				toCol = 104 - move.charAt(0);
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

	public static boolean menu() throws FileNotFoundException, ClassNotFoundException, IOException {
		System.out.println(
				"\n\nMenu\n0. Main menu\n1. Save game\n2. Change debug mode\n3. Change gameplay mode\n4. Continue game");
		int choice = kyb.nextInt();
		switch (choice) {
		case 0:
			return true;
		case 1:
			CB.saveGame();
			break;
		case 2:
			CB.reverseDebug();
			break;
		case 3:
			CB.reverseCpu();
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
		CB.setLocal(net.isHost());
		try {
			playGame();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			CB.endNet();
			net.close();
		}
	}

	public static boolean netMove() throws IOException, ClassNotFoundException {
		if (CB.getLocal()) {
			CB.displayChoice();
			if (movePiece())
				return true;
			// net.sendServerData(fr, fc, tr, tc);
		} else {
			CB.displayChoice();
			System.out.println("\nWaiting for other players move");
			int[] data = net.getClientData();
			CB.performMove(data[1], data[0], data[3], data[2]);
		}
		return false;
	}

	public static void promote(int row, int col) {
		CB.displayChoice();
		int choice;
		do {
			System.out.println("\nWhat would you like to convert your pawn to?");
			System.out.println("1. Queen\n2. Bishop\n3. Rook\n4. Horse");
			choice = kyb.nextInt();
			if (choice < 1 || choice > 4)
				System.out.println("Not a valid choice, 1-4");
		} while (choice < 1 || choice > 4);
		CB.promotion(row, col, choice);
	}

	public static void setErrorMessage(String errorMessage) {
		ChessDriver.errorMessage = errorMessage;
	}

}