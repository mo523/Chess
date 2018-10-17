import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class ChessDriver
{
	static Scanner kyb = new Scanner(System.in);

	public static void main( String[] args ) throws InterruptedException, IOException
	{
		Piece CB[][] = new Piece[8][8];
		setUpPieces(CB);
		/*
		 * System.out.println("Player 1 (white), what is your name?");// String p1 =
		 * kyb.nextLine(); System.out.println("Player 2 (black), what is your name?");
		 * String p2 = kyb.nextLine();
		 */
		playGame("p1", "p2", CB);
		kyb.close();
	}

	public static void playGame( String p1, String p2, Piece[][] CB ) throws InterruptedException, IOException
	{
		Piece wk = CB[0][4];
		Piece bk = CB[7][4];
		do
		{
			if ( notInCheckMate(wk) )
				movePiece(true, CB, p1);
			else
				break;
			if ( notInCheckMate(bk) )
				movePiece(false, CB, p2);
			else
				break;
		} while ( true );
	}

	public static void movePiece( boolean white, Piece[][] CB, String name ) throws InterruptedException, IOException
	{
		String to, from;
		boolean legalMove = true;
		int fx, fy, tx, ty;
		do
		{
			clear();
			display(CB);
			if ( !legalMove )
				System.out.println(name + ", You do not have a piece there\nPlease try again;");
			System.out.println(name + ", Which piece would you like to move?");
			from = kyb.nextLine();
			fx = convertPositionToNumbers(from);
			fy = from.charAt(1) - 48;
			legalMove = isValidPieceThere(fx, fy, CB, white);
		} while ( !legalMove );
		do
		{
			clear();
			display(CB);
			if ( !legalMove )
				System.out.println("Illegal complete move, try again");
			System.out.println(name + ", Where would you like to move your piece to?");
			to = kyb.nextLine();
			tx = convertPositionToNumbers(to);
			ty = from.charAt(1) - 48;
			legalMove = true;
		} while ( !legalMove );
		CB[tx][ty] = CB[fx][fy];
		CB[fx][fy] = null;
	}

	public static boolean canMoveThere( int fx, int fy, int tx, int ty, Piece[][] CB )
	{
		return true;
	}

	public static boolean isValidPieceThere( int x, int y, Piece[][] CB, boolean white )
	{
		System.out.println(x + " " + y);
		if ( x == -1 || y == -1 || CB[x][y] == null || CB[x][y].white != white )
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

	public static int convertPositionToNumbers( String move )
	{
		int position;
		switch ( move.charAt(0) )
		{
		case 'a':
			position = 0;
			break;
		case 'b':
			position = 1;
			break;
		case 'c':
			position = 2;
			break;
		case 'd':
			position = 3;
			break;
		case 'e':
			position = 4;
			break;
		case 'f':
			position = 5;
			break;
		case 'g':
			position = 6;
			break;
		case 'h':
			position = 7;
			break;
		default:
			position = -1;
			break;
		}
		return position;
	}

	public static boolean notInCheckMate( Piece king )
	{
		return true;
	}

	public static void setUpPieces( Piece[][] CB )
	{
		CB[1][0] = new Pawn(true);
		CB[1][1] = new Pawn(true);
		CB[1][2] = new Pawn(true);
		CB[1][3] = new Pawn(true);
		CB[1][4] = new Pawn(true);
		CB[1][5] = new Pawn(true);
		CB[1][6] = new Pawn(true);
		CB[1][7] = new Pawn(true);
		CB[6][0] = new Pawn(false);
		CB[6][1] = new Pawn(false);
		CB[6][2] = new Pawn(false);
		CB[6][3] = new Pawn(false);
		CB[6][4] = new Pawn(false);
		CB[6][5] = new Pawn(false);
		CB[6][6] = new Pawn(false);
		CB[6][7] = new Pawn(false);
		CB[0][0] = new Rook(true);
		CB[0][1] = new Horse(true);
		CB[0][2] = new Bishop(true);
		CB[0][3] = new Queen(true);
		CB[0][4] = new King(true);
		CB[0][5] = new Bishop(true);
		CB[0][6] = new Horse(true);
		CB[0][7] = new Rook(true);
		CB[7][0] = new Rook(false);
		CB[7][1] = new Horse(false);
		CB[7][2] = new Bishop(false);
		CB[7][3] = new Queen(false);
		CB[7][4] = new King(false);
		CB[7][5] = new Bishop(false);
		CB[7][6] = new Horse(false);
		CB[7][7] = new Rook(false);
	}

	public static String PieceSection( int i, int j, Piece[][] cb )
	{
		String fourteen;
		if ( i % 6 == 0 || i % 6 == 5 )
		{
			fourteen = "              ";
		}
		else
			fourteen = cb[i / 6][j].getIcon(i%6);
		return fourteen;
	}

	public static void display( Piece[][] CB )
	{
		
		AnsiConsole.systemInstall();
		System.out.println();
		System.out.print(" ");
		for ( int i = 0; i < 116; i++ )
		{
			System.out.print(ansi().bgBright(WHITE).a(" ").reset());
		}
		System.out.println(ansi().reset());
		for ( int i = 0; i < 48; i++ )
		{
			System.out.print(" ");
			System.out.print(ansi().bgBright(WHITE).a("  ").reset());
			for ( int j = 0; j < 8; j++ )
			{
				if ( j % 2 == 0 )
					if ( i / 6 % 2 == 0 )
						if ( CB[i / 6][j] == null )
							System.out.print(ansi().bgBright(BLACK).fg(BLUE).a("              ").reset());
						else if ( CB[i / 6][j].isWhite() )
							System.out.print(ansi().bgBright(BLACK).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
						else
							System.out.print(ansi().bgBright(BLACK).fg(BLACK).a(PieceSection(i, j, CB)).reset());
					else if ( CB[i / 6][j] == null )
						System.out.print(ansi().bg(WHITE).fg(BLUE).a("              ").reset());
					else if ( CB[i / 6][j].isWhite() )
						System.out.print(ansi().bg(WHITE).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
					else
						System.out.print(ansi().bg(WHITE).fg(BLACK).a(PieceSection(i, j, CB)).reset());
				else if ( i / 6 % 2 == 0 )
					if ( CB[i / 6][j] == null )
						System.out.print(ansi().bg(WHITE).fg(BLUE).a("              ").reset());
					else if ( CB[i / 6][j].isWhite() )
						System.out.print(ansi().bg(WHITE).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
					else
						System.out.print(ansi().bg(WHITE).fg(BLACK).a(PieceSection(i, j, CB)).reset());
				else if ( CB[i / 6][j] == null )
					System.out.print(ansi().bgBright(BLACK).fg(BLUE).a("              ").reset());
				else if ( CB[i / 6][j].isWhite() )
					System.out.print(ansi().bgBright(BLACK).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
				else
					System.out.print(ansi().bgBright(BLACK).fg(BLACK).a(PieceSection(i, j, CB)).reset());
				
			}
			System.out.print(ansi().bgBright(WHITE).a("  ").reset());
			System.out.println();
		}
		System.out.print(" ");
		for ( int i = 0; i < 116; i++ )
			System.out.print(ansi().bgBright(WHITE).a(" ").reset());
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
