import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class ChessDriver
{
	static Scanner kyb = new Scanner(System.in);
	static final boolean IS_WHITE = true;// this is to make what color the pieces are more clear (rather than using true
											// and false)
	static final boolean IS_BLACK = false;// this is to make what color the pieces are more clear (rather than using
											// true and false)

	public static void main( String[] args ) throws InterruptedException, IOException
	{

		Piece CB[][] = new Piece[8][8];
		setUpPieces(CB);
		/*
		 * System.out.println("Player 1 (white), what is your name?");// String p1 =
		 * kyb.nextLine(); System.out.println("Player 2 (black), what is your name?");
		 * String p2 = kyb.nextLine();
		 */
		playGame("p1", "p2", CB);// remember to take out the literal p1 and p2
		kyb.close();
	}

	public static void playGame( String p1, String p2, Piece[][] CB ) throws InterruptedException, IOException
	{
		Piece whiteKing = CB[0][4];
		Piece blackKing = CB[7][4];
		do
		{
			if ( notInCheckMate(whiteKing) )
				movePiece(IS_WHITE, CB, p1);
			else
				break;
			if ( notInCheckMate(blackKing) )
				movePiece(IS_BLACK, CB, p2);
			else
				break;
		} while ( notInCheckMate(whiteKing) && notInCheckMate(blackKing) );
	}

	public static void movePiece( boolean localWhite, Piece[][] CB, String name )
			throws InterruptedException, IOException
	{
		// method needs to be cut in half
		boolean canPieceMoveThereBasedOnAllItsRules = true;
		String from, to;
		boolean legalMoveInput = true;
		final boolean IS_FROM = true;
		final boolean IS_TO = false;
		int from_Y_Coordinate, from_X_Coordinate, to_Y_Coordinate, to_X_Coordinate;
		do
		{
			if ( !canPieceMoveThereBasedOnAllItsRules )
				System.out.println("Bad Move, try again");
			do
			{
				clear();
				display(CB);
				if ( !legalMoveInput )
					System.out.println(name + ", You do not have a piece there\nPlease try again;");
				System.out.println(name + ", Which piece would you like to move?");
				from = kyb.nextLine().toLowerCase();
				from_Y_Coordinate = from.charAt(0) - 97;
				from_X_Coordinate = from.charAt(1) - 49;

				legalMoveInput = isValidPieceThere(from, from_Y_Coordinate, from_X_Coordinate, CB, localWhite, IS_FROM);
			} while ( !legalMoveInput );
			do
			{
				clear();
				display(CB);
				if ( !legalMoveInput )
					System.out.println("Illegal complete move, try again");
				System.out.println(name + ", Where would you like to move your piece to?");
				to = kyb.nextLine().toLowerCase();
				to_Y_Coordinate = to.charAt(0) - 97;
				to_X_Coordinate = to.charAt(1) - 49;
				legalMoveInput = isValidPieceThere(from, from_Y_Coordinate, from_X_Coordinate, CB, localWhite, IS_TO);
			} while ( !legalMoveInput );
			// make sure its not the same space and canmovethere
		} while ( !canPieceMoveThereBasedOnAllItsRules );
		CB[to_X_Coordinate][to_Y_Coordinate] = CB[from_X_Coordinate][from_Y_Coordinate];
		CB[from_X_Coordinate][from_Y_Coordinate] = null;
	}

	// call to piece method if valid move, add do while loop in last method
	public static boolean canMoveThere( int from_Y_Coordinate, int from_X_Coordinate, int to_Y_Coordinate,
			int to_X_Coordinate, Piece[][] CB )
	{
		return true;
	}

	public static boolean isValidPieceThere( String inputPosition, int y_Coordinate, int x_Coordinate, Piece[][] CB,
			boolean localWhite, boolean from )
	{

		if ( inputPosition.length() != 2 || y_Coordinate < 0 || y_Coordinate > 7 || x_Coordinate < 0
				|| x_Coordinate > 7 )
			return false;
		else if ( from
				&& ( CB[x_Coordinate][y_Coordinate] == null || CB[x_Coordinate][y_Coordinate].white != localWhite ) )
			return false;
		else
			return true;
	}
	// public static String getMoveValuesAndTestThem( String moveValue )
	// {
	// boolean goodMove = true;
	// do
	// {
	// if ( !goodMove )
	// System.out.println("Illegal individual position, try again");
	// moveValue = convertPositionToNumbers(kyb.next());
	// goodMove = ( moveValue.charAt(0) >= 48 && moveValue.charAt(0) <= 55 &&
	// moveValue.charAt(1) >= 48
	// && moveValue.charAt(1) <= 55 );
	// } while ( !goodMove );
	// return moveValue;
	// }

	public static boolean notInCheckMate( Piece king )
	{
		return true;
	}

	public static void setUpPieces( Piece[][] CB )
	{
		CB[1][0] = new Pawn(IS_WHITE);
		CB[1][1] = new Pawn(IS_WHITE);
		CB[1][2] = new Pawn(IS_WHITE);
		CB[1][3] = new Pawn(IS_WHITE);
		CB[1][4] = new Pawn(IS_WHITE);
		CB[1][5] = new Pawn(IS_WHITE);
		CB[1][6] = new Pawn(IS_WHITE);
		CB[1][7] = new Pawn(IS_WHITE);
		CB[6][0] = new Pawn(IS_BLACK);
		CB[6][1] = new Pawn(IS_BLACK);
		CB[6][2] = new Pawn(IS_BLACK);
		CB[6][3] = new Pawn(IS_BLACK);
		CB[6][4] = new Pawn(IS_BLACK);
		CB[6][5] = new Pawn(IS_BLACK);
		CB[6][6] = new Pawn(IS_BLACK);
		CB[6][7] = new Pawn(IS_BLACK);
		CB[0][0] = new Rook(IS_WHITE);
		CB[0][1] = new Horse(IS_WHITE);
		CB[0][2] = new Bishop(IS_WHITE);
		CB[0][3] = new Queen(IS_WHITE);
		CB[0][4] = new King(IS_WHITE);
		CB[0][5] = new Bishop(IS_WHITE);
		CB[0][6] = new Horse(IS_WHITE);
		CB[0][7] = new Rook(IS_WHITE);
		CB[7][0] = new Rook(IS_BLACK);
		CB[7][1] = new Horse(IS_BLACK);
		CB[7][2] = new Bishop(IS_BLACK);
		CB[7][3] = new Queen(IS_BLACK);
		CB[7][4] = new King(IS_BLACK);
		CB[7][5] = new Bishop(IS_BLACK);
		CB[7][6] = new Horse(IS_BLACK);
		CB[7][7] = new Rook(IS_BLACK);
	}

	public static String PieceSection( int i, int j, Piece[][] cb )
	{
		String fourteen;
		if ( i % 6 == 0 )
			fourteen = "              ";
		else if (i % 6 == 5)
			fourteen = "            " +  (char)(j+65) + (i/6+1);
		else
			fourteen = cb[i / 6][j].getIcon(i % 6 - 1);
		return fourteen;
	}

	public static void display( Piece[][] CB )
	{

		AnsiConsole.systemInstall();
		System.out.println();
		System.out.print(" ");

		System.out.print(ansi().bgBright(WHITE).fg(BLACK).a(
				"        A             B             C             D             E             F             G             H         ")
				.reset());
		System.out.println(ansi().reset());
		for ( int i = 0; i < 48; i++ )
		{
			System.out.print(" ");
			if ( i % 6 + 1 == 3 )
				System.out.print(ansi().bgBright(WHITE).fg(BLACK).a((i/6+1) + " ").reset());
			else
				System.out.print(ansi().bgBright(WHITE).a("  ").reset());
			for ( int j = 0; j < 8; j++ )
			{
				if ( j % 2 == 0 )
					if ( i / 6 % 2 == 0 )
						if ( CB[i / 6][j] == null )
							if (i%6==5)
								System.out.print(ansi().bgBright(BLACK).fg(BLUE).a(PieceSection(i, j, CB)).reset());
							else
								System.out.print(ansi().bgBright(BLACK).fg(BLUE).a("              ").reset());
						else if ( CB[i / 6][j].isWhite() )
							System.out.print(ansi().bgBright(BLACK).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
						else
							System.out.print(ansi().bgBright(BLACK).fg(BLACK).a(PieceSection(i, j, CB)).reset());
					else if ( CB[i / 6][j] == null )
						if (i%6==5)
							System.out.print(ansi().bg(WHITE).fg(BLUE).a(PieceSection(i, j, CB)).reset());
						else
							System.out.print(ansi().bg(WHITE).fg(BLUE).a("              ").reset());
					else if ( CB[i / 6][j].isWhite() )
						System.out.print(ansi().bg(WHITE).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
					else
						System.out.print(ansi().bg(WHITE).fg(BLACK).a(PieceSection(i, j, CB)).reset());
				else if ( i / 6 % 2 == 0 )
					if ( CB[i / 6][j] == null )
						if (i%6==5)
							System.out.print(ansi().bg(WHITE).fg(BLUE).a(PieceSection(i, j, CB)).reset());
						else
							System.out.print(ansi().bg(WHITE).fg(BLUE).a("              ").reset());
					else if ( CB[i / 6][j].isWhite() )
						System.out.print(ansi().bg(WHITE).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
					else
						System.out.print(ansi().bg(WHITE).fg(BLACK).a(PieceSection(i, j, CB)).reset());
				else if ( CB[i / 6][j] == null )
					if (i%6==5)
						System.out.print(ansi().bgBright(BLACK).fg(BLUE).a(PieceSection(i, j, CB)).reset());
					else
						System.out.print(ansi().bgBright(BLACK).fg(BLUE).a("              ").reset());
				else if ( CB[i / 6][j].isWhite() )
					System.out.print(ansi().bgBright(BLACK).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
				else
					System.out.print(ansi().bgBright(BLACK).fg(BLACK).a(PieceSection(i, j, CB)).reset());

			}
			if ( i % 6 == 3 )
				System.out.print(ansi().bgBright(WHITE).fg(BLACK).a(" " + (i/6+1)).reset());
			else
				System.out.print(ansi().bgBright(WHITE).a("  ").reset());
			System.out.println();
		}
		System.out.print(" ");
		System.out.print(ansi().bgBright(WHITE).fg(BLACK).a(
				"         A             B             C             D             E             F             G             H        ")
				.reset());
		System.out.println();
		AnsiConsole.systemUninstall();
	}

	public static void clear() throws InterruptedException, IOException
	{
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}

	public static void debugBoard( Piece[][] CB )
	{
		for ( int i = 0; i < 8; i++ )
		{
			for ( int j = 0; j < 8; j++ )
			{
				System.out.print(CB[i][j] + " ");
			}
			System.out.println();
		}
	}
}
