package chess;

//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ChessDriver {
	private static Scanner kyb = new Scanner(System.in);
	private static ChessBoard CB;
	private static String errorMessage;

	public static void main(String[] args) {
		System.out.println("Welcome to chess!");
		initialMenu();
		kyb.close();
	}

	public static void initialMenu() {
		int choice;
		boolean debug = false;
		boolean cpuGame = false;
		boolean cpuTurn = false;
		boolean networkGame = false;
		boolean useJansi = true;
		SavedGame s = null;
		do {
			System.out.println(
					"\n\nMain Menu\n\n0. Quit\n1. New Game (Two Player) \n2. New game (One Player)\n3. New networked game"
							+ "\n4. Open saved game\n5. Continue Game\n\n6. 1 with debug\n7. 2 with debug\n8. 3 with debug"
							+ "\n9. Zero Players\n-1. no jansi");
			do {
				choice = kyb.nextInt();
			} while (choice < -1 || choice > 9);
			switch (choice) {
			case -1:
				useJansi = false;
				break;
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
				CB = new ChessBoard(false, false, false, true, false);
				try {
					networkedGame();
					networkGame = true;
				} catch (IOException ex) {
					System.out.println("Error!!\n" + ex);
					choice = 0;
				}
				break;
			case 4:
				// TODO
				// FIX SAVED GAME
				// s = SaveGameFunctionality.loadSavedGame();
				break;
			default: // 0, 1, 5
				break;
			}
			if (CB == null)
				CB = new ChessBoard(debug, cpuGame, cpuTurn, networkGame, useJansi);
			if (choice != 0) {
				if (s != null)
					CB.loadGame(s);
				System.out.println();
				playGame();
			}
		} while (choice != 0);
	}

	public static void playGame() {
		CB.displayChoice();
		do {
			if (CB.getCpuGame() && !CB.getTurn())
				AI.cpuMovePiece(CB);
			else if (CB.getNetGame() && !CB.getTurn())
				CB.netMove();
			else if (movePiece())
				break;
			CB.displayChoice();
		} while (CB.gameStatus());
	}

	public static boolean movePiece() {
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

	public static boolean menu() {
		System.out.println(
				"\n\nMenu\n0. Main menu\n1. Save game\n2. Change debug mode\n3. Change gameplay mode\n4. Continue game");
		int choice = kyb.nextInt();
		switch (choice) {
		case 0:
			return true;
		case 1:
			// TODO implement save game
			// CB.saveGame();
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

	public static void networkedGame() throws IOException {
		System.out.println("Would you like to:\n1. Host a game\n2. Join a game");
		int choice;
		do {
			choice = kyb.nextInt();
			if (choice != 1 && choice != 2)
				System.out.println("Bad choice. 1 or 2");
		} while (choice != 1 && choice != 2);
		if (choice == 1) {
			CB.setNet(new Network());
		} else {
			System.out.println("IP Address?\tType '0' to play on same computer");
			kyb.nextLine();
			String ip = kyb.nextLine();
			if (ip.equals("0"))
				ip = "127.0.0.1";
			CB.setNet(new Network(ip));
		}
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