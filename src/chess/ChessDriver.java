package chess;

import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

public class ChessDriver
{
	private static Scanner kyb = new Scanner(System.in);
	private static ChessBoard CB;
	private static String errorMessage;

	public static void main(String[] args)
	{
		System.out.println("Welcome to chess!");
		initialMenu();
		kyb.close();
	}

	public static void initialMenu()
	{
		int choice;
		boolean useJansi = !System.getProperty("user.name").equalsIgnoreCase("moshe");
		if (useJansi)
			AnsiConsole.systemInstall();
		do
		{
			boolean debug = false;
			boolean cpuGame = false;
			boolean playerTurn = false;
			boolean networkGame = false;
			boolean randomGame = false;

			Network net = null;
			System.out.println(
					"\n\nMain Menu\n\n0. Quit\n1. New Game (Two Player Local) \n2. New game (One Player vs. Cpu)\n3. New networked game"
							+ "\n4. 1 with random board\n5. 2 with random board\n6. Open saved game\n7. Continue Game");
			// + "\n\n-1..-5. 1..5 with debug");
			do
			{
				choice = kyb.nextInt();
			} while (choice < -5 || choice > 7);
			if (choice < 0)
			{
				debug = true;
				choice *= -1;
			}
			if (choice == 4 || choice == 5)
			{
				randomGame = true;
				choice -= 3;
			}
			switch (choice)
			{
				case 2:
					System.out.println("(W)hite or (B)lack?");
					playerTurn = kyb.next().toUpperCase().equals("W") ? true : false;
					cpuGame = true;
					break;
				case 3:
					try
					{
						net = networkedGame();
					}
					catch (IOException ex)
					{
						ex.printStackTrace();
						System.out.println("Error. Network connection failed");
						continue;
					}
					networkGame = true;
					break;
				case 6:
					try
					{
						CB = SaveGameFunctionality.loadSavedGame();
					}
					catch (ClassNotFoundException | IOException e)
					{
						e.printStackTrace();
					}
					break;
				default: // 0, 1, 7
					break;
			}

			if (choice != 0)
			{
				if (choice != 6 && choice != 7)
					CB = new ChessBoard(debug, cpuGame, playerTurn, networkGame, useJansi, randomGame);
				if (networkGame)
					CB.setNet(net);
				playGame();
			}
		} while (choice != 0);
		if (useJansi)
			AnsiConsole.systemUninstall();
	}

	public static void playGame()
	{
		if (CB.getCpuGame())
		{
			AI.cpuMovePiece(CB);

		}
		else
		{
			CB.displayChoice();

			do
			{
				if (CB.getNetGame() && !CB.getTurn())
					CB.netMove();
				else if (movePiece())
					break;
				CB.displayChoice();
			} while (CB.gameStatus());
		}
	}

	public static boolean movePiece()
	{
		String name = CB.getWhite() ? "White" : "Black";
		boolean canMoveThere = true;
		String move;
		boolean legalMoveInput = true;
		int fromRow, fromCol, toRow, toCol;

		do
		{
			if (!canMoveThere)
				System.out.println(errorMessage);
			System.out.println("\n" + name + ", Which piece would you like to move?\nType 'm' for menu");
			do
			{
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
			do
			{
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

	public static String getPosition()
	{
		String pos;
		boolean badInput = false;
		do
		{
			badInput = true;
			pos = kyb.next().toLowerCase();
			if (pos.equalsIgnoreCase("m"))
			{
				if (menu())
					return pos;
			}
			else if (pos.length() != 2)
				System.out.println("\nPosition must be 2 characters, try again.");
			else if (pos.charAt(0) < 97 || pos.charAt(0) > 104 || pos.charAt(1) < 49 || pos.charAt(1) > 56)
				System.out.println("\nInvalid position entry. It must be a [a-h][1-8], try again.");
			else
				badInput = false;
		} while (badInput);
		return pos;
	}

	public static boolean menu()
	{
		System.out.println(
				"\n\nMenu\n0. Main menu\n1. Save game\n2. Change debug mode\n3. Change gameplay mode\n4. Continue game");
		int choice = kyb.nextInt();
		switch (choice)
		{
			case 0:
				return true;
			case 1:
				try
				{
					SaveGameFunctionality.saveGame(CB);
				}
				catch (ClassNotFoundException | IOException e)
				{
					e.printStackTrace();
				}
				return true;
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

	public static Network networkedGame() throws IOException
	{
		System.out.println("Would you like to:\n1. Host a game\n2. Join a game");
		int choice;
		do
		{
			choice = kyb.nextInt();
			if (choice != 1 && choice != 2)
				System.out.println("Bad choice. 1 or 2");
		} while (choice != 1 && choice != 2);
		if (choice == 1)
		{
			return new Network();
		}
		else
		{
			System.out.println("IP Address?\tType '0' to play on same computer");
			kyb.nextLine();
			String ip = kyb.nextLine();
			if (ip.equals("0"))
				ip = "127.0.0.1";
			if (ip.equals("1"))
				ip = "192.168.1.100";
			return new Network(ip);
		}
	}

	public static void promote(int row, int col)
	{
		CB.displayChoice();
		int choice;
		do
		{
			System.out.println("\nWhat would you like to convert your pawn to?");
			System.out.println("1. Queen\n2. Bishop\n3. Rook\n4. Horse");
			choice = kyb.nextInt();
			if (choice < 1 || choice > 4)
				System.out.println("Not a valid choice, 1-4");
		} while (choice < 1 || choice > 4);
		CB.promotion(row, col, choice);
	}

	public static void setErrorMessage(String errorMessage)
	{
		ChessDriver.errorMessage = errorMessage;
	}
}