package chess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

public class ChessDriver
{
	private static Scanner kb = new Scanner(System.in);
	private static ChessBoard CB;

	public static void main(String[] args)
	{
		System.out.println("Welcome to chess!");
		AnsiConsole.systemInstall();
		initialMenu();
		AnsiConsole.systemUninstall();
		kb.close();
	}

	private static void initialMenu()
	{
		int choice;

		do
		{
			Network net = null;
			boolean debug = false;
			int ai = 0;
			boolean playerTurn = false;
			boolean randomGame = false;
			System.out.println(
					"\n\nMain Menu\n\n0. Quit\n1. New Game (Two Player Local) \n2. New game (One Player vs. Cpu)\n3. New networked game"
							+ "\n4. 1 with random board\n5. 2 with random board\n6. Open saved game\n7. Continue Game");
			// + "\n\n-1..-5. 1..5 with debug");
			do
			{
				choice = kb.nextInt();
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
					playerTurn = kb.next().toUpperCase().equals("W") ? true : false;
					System.out.println("\n1 Play Dumb Computer \n2 Play Easy Computer \n3 Play Moderate Computer"
							+ "\n4 Play Hard Computer  \n5 To go back to main menu \n WARNING!! the harder the computer the "
							+ "longer it will take for the computer");
					ai = kb.nextInt();
					break;
				case 3:
					try
					{
						if (net == null)
							net = new Network(kb);
						playerTurn = netMenu(net);
					}
					catch (IOException | ClassNotFoundException ex)
					{
						ex.printStackTrace();
						System.out.println("Error. Network connection failed");
						continue;
					}
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

			if (choice != 0 && choice != 6 && choice != 7)
			{
				CB = new ChessBoard(debug, ai, playerTurn, net, randomGame);
				playGame();
			}
		} while (choice != 0);
	}

	private static boolean netMenu(Network net) throws IOException, ClassNotFoundException
	{
		int choice;
		System.out.println("Welcome to the Chess Lobby\n0. Exit lobby\n1. Host game\n2. Join game\n3. Watch game");
		choice = kb.nextInt();
		switch (choice)
		{
			case 1:
				net.hostGame();
				break;
			case 2:
				ArrayList<String> players = net.getPlayers();
				System.out.println("Who would you like to play against");
				for (int i = 0; i < players.size(); i++)
					System.out.println((i + 1) + ". " + players.get(i));
				net.joinGame(players.get(kb.nextInt() - 1));
				break;
			case 3:

				break;

			default:
				break;
		}
		return choice == 1;
	}

	private static void playGame()
	{
		do
		{
			if (CB.getNetGame() && !CB.getTurn())
				CB.netMove();
			else if (CB.getCpuGame() && !CB.getTurn())
				CB.AIMove();
			else
			{
				CB.displayChoice();
				if (movePiece())
					break;
			}
		} while (CB.gameStatus());
	}

	private static boolean movePiece()
	{
		String name = CB.getWhite() ? "White" : "Black";
		boolean canMoveThere = true;
		String move;
		boolean legalMoveInput = true;
		int fromRow, fromCol, toRow, toCol;

		do
		{
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
			canMoveThere = CB.canMoveThere(fromRow, fromCol, toRow, toCol, false);
		} while (!canMoveThere);

		boolean promote = CB.performMove(fromRow, fromCol, toRow, toCol);
		if (promote)
			promote(toRow, toCol);

		return false;
	}

	private static String getPosition()
	{
		String pos;
		boolean badInput = false;
		do
		{
			badInput = true;
			pos = kb.next().toLowerCase();
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

	private static boolean menu()
	{
		System.out.println(
				"\n\nMenu\n0. Main menu\n1. Save game\n2. Change debug mode\n3. Change gameplay mode\n4. Continue game");
		int choice = kb.nextInt();
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

	private static void promote(int row, int col)
	{
		CB.displayChoice();
		int choice;
		do
		{
			System.out.println("\nWhat would you like to convert your pawn to?");
			System.out.println("1. Queen\n2. Bishop\n3. Rook\n4. Horse");
			choice = kb.nextInt();
			if (choice < 1 || choice > 4)
				System.out.println("Not a valid choice, 1-4");
		} while (choice < 1 || choice > 4);
		CB.promotion(row, col, choice);
	}
}
