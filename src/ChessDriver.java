import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class ChessDriver
{
	static Scanner kyb = new Scanner(System.in);

	public static void main( String[] args )
	{
		Piece CB[][] = new Piece[8][8];
		setUpPieces(CB);

		display(CB);
		System.out.println("Player 1 (white), what is your name?");//
		String p1 = kyb.nextLine();
		System.out.println("Player 2 (black), what is your name?");
		String p2 = kyb.nextLine();

		playGame(p1, p2);
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
		CB[7][3] = new King(false);
		CB[7][4] = new Queen(false);
		CB[7][5] = new Bishop(false);
		CB[7][6] = new Horse(false);
		CB[7][7] = new Rook(false);
	}

	public static void playGame( Player p1, Player p2 )
	{

		do
		{
			if ( p1.isNotInCheckMate() )
				eachPlayersMove(p1, p2);
			if ( p2.isNotInCheckMate() )
				eachPlayersMove(p2, p1);
		} while ( p1.isNotInCheckMate() && p2.isNotInCheckMate() );
	}

	public static void eachPlayersMove( Player movingPlayer, Player passivePlayer )
	{
		boolean legalMove = true;
		String from = null;
		String to = null;

		displayBoard();

		do
		{
			if ( !legalMove )
				System.out.println("Illegal complete move, try again");
			System.out.println(movingPlayer.getName() + ", what is your moves first position?");
			from = getMoveValuesAndTestThem(from);
			System.out.println(movingPlayer.getName() + ", what is your moves second position?");
			to = getMoveValuesAndTestThem(to);
			legalMove = movingPlayer.movePiece(from, to);
			if ( legalMove )
				killPiece(passivePlayer, to);
		} while ( !legalMove );
	}

	public static String getMoveValuesAndTestThem( String moveValue )
	{
		boolean goodMove = true;
		do
		{
			if ( !goodMove )
				System.out.println("Illegal individual position, try again");
			moveValue = convertPositionToNumbers(kyb.next());
			goodMove = ( moveValue.charAt(0) >= 48 && moveValue.charAt(0) <= 55 && moveValue.charAt(1) >= 48
					&& moveValue.charAt(1) <= 55 );
		} while ( !goodMove );
		return moveValue;
	}

	public static void killPiece( Player p, String to )
	{
		for ( int i = 0; i < Max_Num_Of_Pieces; i++ )
		{
			if ( p.getPieces()[i].getPosition().equalsIgnoreCase(to) )
			{
				p.getPieces()[i] = null;
				break;
			}
		}
	}

	public static void displayBoard()
	{

	}

	public static String convertPositionToNumbers( String move )
	{
		String positionInNumbers = "";
		switch ( move.charAt(0) )
		{
		case 'a':
			positionInNumbers += "0";
			break;
		case 'b':
			positionInNumbers += "1";
			break;
		case 'c':
			positionInNumbers += "2";
			break;
		case 'd':
			positionInNumbers += "3";
			break;
		case 'e':
			positionInNumbers += "4";
			break;
		case 'f':
			positionInNumbers += "5";
			break;
		case 'g':
			positionInNumbers += "6";
			break;
		case 'h':
			positionInNumbers += "7";
			break;
		default:
			positionInNumbers += "8";
			break;
		}
		positionInNumbers += Integer.toString(( Integer.parseInt(move.substring(1)) - 1 ));
		return positionInNumbers;
	}

	public static void display( Piece[][] CB )
	{
		AnsiConsole.systemInstall();

		for ( int i = 0; i < 48; i++ )
		{
			for ( int j = 0; j < 8; j++ )
			{
				if ( j % 2 == 0 )
					if ( i / 6 % 2 == 0 )
						if ( CB[i / 6][j] == null )
							System.out.print(ansi().bg(YELLOW).fg(BLUE).a("              ").reset());
						else if ( CB[i / 6][j].isWhite() )
							System.out.print(ansi().bg(YELLOW).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
						else
							System.out.print(ansi().bg(YELLOW).fg(BLACK).a(PieceSection(i, j, CB)).reset());
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
					System.out.print(ansi().bg(YELLOW).fg(BLUE).a("              ").reset());
				else if ( CB[i / 6][j].isWhite() )
					System.out.print(ansi().bg(YELLOW).fgBright(WHITE).a(PieceSection(i, j, CB)).reset());
				else
					System.out.print(ansi().bg(YELLOW).fg(BLACK).a(PieceSection(i, j, CB)).reset());

			}
			System.out.println();
		}
		System.out.println("test");
		AnsiConsole.systemUninstall();
	}

	public static String PieceSection( int i, int j, Piece[][] cb )
	{
		String fourteen;
		if ( i % 6 == 0 || i % 6 == 5 )
		{
			fourteen = "              ";
		}
		else
			fourteen = cb[i / 6][j].getIcon(1);
		return fourteen;
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
