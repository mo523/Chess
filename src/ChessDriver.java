import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

public class ChessDriver
{

	static Scanner kyb = new Scanner(System.in);
	static final boolean IS_WHITE = true;// this is to make what color the pieces are more clear (rather than using true
											// and false)
	static final boolean IS_BLACK = false;// this is to make what color the pieces are more clear (rather than using//
											// true and false)
	static boolean debug;
	static Piece whiteKing, blackKing;
	static boolean whitesTurn = true;;
	static Piece[][] chessBoard = new Piece[8][8];

	public static void main( String[] args ) throws InterruptedException, IOException
	{
		System.out.println("(R)egular mode\n(D)ebug mode");
		debug = kyb.nextLine().toUpperCase().equals("D") ? true : false;

		AnsiConsole.systemInstall();
		setUpPieces();
		/*
		 * System.out.println("Player 1 (white), what is your name?");// String p1 =
		 * kyb.nextLine(); System.out.println("Player 2 (black), what is your name?");
		 * String p2 = kyb.nextLine();
		 */
		playGame();// remember to take out the literal p1 and p2
		kyb.close();
		AnsiConsole.systemUninstall();
	}

	public static void playGame() throws InterruptedException, IOException
	{
		do
		{
			if (notInCheckMate())
			{
				if ( debug )
					displayDebug();
				else
					display();
				if (isInCheck())
					System.out.println("\nWarning! Your king is in check!\n");
				movePiece();
			}
			whitesTurn = !whitesTurn;
		}	while(notInCheckMate());
		
		System.out.println("Sorry " + (whitesTurn ? "White" : "Black") + ", checkmate, you lose.");
	}

	public static void movePiece() throws InterruptedException, IOException
	{
		String name = whitesTurn ? "White" : "Black"; 
		// method needs to be cut in half
		boolean canPieceMoveThereBasedOnAllItsRules = true;
		String move;
		boolean legalMoveInput = true;
		int fromCol, fromRow, toCol, toRow;

		do
		{
			do
			{
				if ( !legalMoveInput )
					System.out.println(name + ", You do not have a piece there\nPlease try again;");
				System.out.println(name + ", Which piece would you like to move?");
				move = getCorrect2CharPosition();
				fromCol = move.charAt(0) - 97;
				fromRow = move.charAt(1) - 49;

				legalMoveInput = isValidPieceThere(fromCol, fromRow);
			} while ( !legalMoveInput );

			System.out.println(name + ", Where would you like to move your piece to?");
			do
			{
				move = getCorrect2CharPosition();
				toCol = move.charAt(0) - 97;
				toRow = move.charAt(1) - 49;
				if ( toCol == fromCol && toRow == fromRow)
					System.out.println("Can't move to same place. Try again.");
			} while ( toCol == fromCol && toRow == fromRow);
				
			canPieceMoveThereBasedOnAllItsRules = canMoveThere(fromCol, fromRow, toCol,
					toRow);

		} while ( !canPieceMoveThereBasedOnAllItsRules );
		performMove(fromCol, fromRow, toCol, toRow);
	}
	// call to piece method if valid move, add do while loop in last method
	public static boolean canMoveThere( int fromCol, int fromRow, int toCol, int toRow )
	{
		return chessBoard[fromRow][fromCol].isLegalMove(fromCol, fromRow,
				toCol, toRow, chessBoard, (whitesTurn ? whiteKing : blackKing));
	}
	
	public static boolean isValidPieceThere( int col, int row )
	{
		if ( chessBoard[row][col] == null || chessBoard[row][col].white != whitesTurn ) 
			return false;
		else
			return true;
	}
	public static String getCorrect2CharPosition()
	{
		String position = makeSureStringIs2Chars();
		position = correctChars(position);
		return position;
	}

	public static String makeSureStringIs2Chars()
	{
		String position = "";
		do
		{
			position = kyb.next().toLowerCase();
			if ( position.length() != 2 )
				System.out.println("Position must be 2 characters");
		} while ( position.length() != 2 );

		return position;
	}

	public static String correctChars( String position )
	{
		while ( position.charAt(0) < 97 || position.charAt(0) > 104 || position.charAt(1) < 49
				|| position.charAt(1) > 56 )
		{
			System.out.println("Invalid position entry. It must be a [a-h] then [1-8]. Try Again");
			position = kyb.next().toLowerCase();
		}
		return position;
	}


	
	public static boolean isInCheck()
	{
		return whitesTurn ? whiteKing.inCheck(whiteKing, chessBoard) : blackKing.inCheck(blackKing, chessBoard);
	}

	public static boolean notInCheckMate( )
	{
		if (!isInCheck())
			return true;
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					if(chessBoard[i][j] != null && chessBoard[i][j].isWhite() == whitesTurn)
						for (int x = 0; x < 8; x++)
							for (int y = 0; y < 8; y++)
								if ( (whitesTurn? whiteKing.notInCheckmate(j, i, y, x, chessBoard, whiteKing) : blackKing.notInCheckmate(j, i, y, x, chessBoard, blackKing)))
									return true;
		return false;
	}


	
	

	//These methods are done and should not be touched
	//Iff you do, note why you touched them
	
	public static void performMove( int fromCol, int fromRow, int toCol,
			int toRow )
	{
		chessBoard[toRow][toCol] = chessBoard[fromRow][fromCol];
		chessBoard[fromRow][fromCol] = null;
	}
	
	public static void setUpPieces()
	{
		// for tests only
		// chessBoard[2][4] = new King(IS_WHITE);
		// chessBoard[5][1] = new Bishop(IS_BLACK);
		// chessBoard[3][3] = new Pawn(IS_WHITE);
		// for tests only

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
		chessBoard[0][3] = new Queen(IS_WHITE);
		chessBoard[0][4] = new King(IS_WHITE);
		chessBoard[0][5] = new Bishop(IS_WHITE);
		chessBoard[0][6] = new Horse(IS_WHITE);
		chessBoard[0][7] = new Rook(IS_WHITE);
		chessBoard[7][0] = new Rook(IS_BLACK);
		chessBoard[7][1] = new Horse(IS_BLACK);
		chessBoard[7][2] = new Bishop(IS_BLACK);
		chessBoard[7][3] = new Queen(IS_BLACK);
		chessBoard[7][4] = new King(IS_BLACK);
		chessBoard[7][5] = new Bishop(IS_BLACK);
		chessBoard[7][6] = new Horse(IS_BLACK);
		chessBoard[7][7] = new Rook(IS_BLACK);
		whiteKing = chessBoard[0][4];
		blackKing = chessBoard[7][4];
		
		//debug for checkmate
		chessBoard[6][4] = chessBoard[0][3];
		chessBoard[6][3] = chessBoard[0][2];
		chessBoard[6][5] = chessBoard[0][5];
		chessBoard[5][4] = chessBoard[0][0];
		chessBoard[0][0] = null;
		chessBoard[0][5] = null;
		chessBoard[0][2] = null;
		chessBoard[0][3] = null;
		
	}
	public static void displayDebug()
	{
		System.out.println("  A  B  C  D  E  F  G  H");
		for ( int i = 0; i < 8; i++ )
		{
			for ( int j = 0; j < 8; j++ )
			{
				if ( chessBoard[i][j] == null )
					System.out.print(( j == 0 ? ( i + 1 ) + " " : "" ) + "nn ");
				else
					System.out.print(( j == 0 ? ( i + 1 ) + " " : "" ) + ( chessBoard[i][j].white ? "w" : "b" )
							+ chessBoard[i][j].toString().charAt(0) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void display()
	{
		int out = whitesTurn ? 0 : 47;
		int in = whitesTurn ? 0 : 7;
		int minOut = whitesTurn ? 48 : -1;
		int minIn = whitesTurn ? 8 : -1;
		int chg = whitesTurn ? 1 : -1;
		String letters1 = "        A             B             C             D             E             F             G             H        ";
		String letters2 = "        H             G             F             E             D             C             B             A        ";
		String reset = "\u001B[0m";
		String bxWhite = "\u001B[107m";
		String bgWhite = "\u001B[47m";
		String bgBlack = "\u001B[100m";
		String fgWhite = "\u001B[97m";
		String fgBlack = "\u001B[30m";
		String fgBlue = "\u001B[34m";
		System.out.println(bxWhite + fgBlack + ( whitesTurn ? letters1 : letters2 ) + " " + reset);
		for ( int i = out; i != minOut; i += chg )
		{
			boolean numRow = i % 6 + 1 == 3;
			System.out.print(bxWhite + fgBlack + ( numRow ? i / 6 + 1 + " " : "  " ) + reset);
			for ( int j = in; j != minIn; j += chg )
			{
				Boolean isWhite = chessBoard[i / 6][j] != null ? chessBoard[i / 6][j].isWhite() : null;
				boolean ijTheSame = i / 6 % 2 == j % 2;
				boolean isNull = chessBoard[i / 6][j] == null;
				System.out.print(( ijTheSame ? bgWhite : bgBlack ) + ( isNull ? fgBlue : isWhite ? fgWhite : fgBlack )
						+ PieceSection(i, j) + reset);
			}
			System.out.print(bxWhite + fgBlack + ( numRow ? " " + ( i / 6 + 1 ) : "  " ) + reset);
			System.out.println();
		}
		System.out.println(bxWhite + fgBlack + " " + ( whitesTurn ? letters1 : letters2 ) + reset);
	}
	public static String PieceSection( int i, int j )
	{
		String fourteen;
		if ( i % 6 == ( whitesTurn ? 5 : 0 ) )
			if ( chessBoard[i / 6][j] == null )
				fourteen = "            " + (char) ( j + 65 ) + ( i / 6 + 1 );
			else
				fourteen = chessBoard[i / 6][j].getIcon(( whitesTurn ? i : 47 - i ) % 6) + (char) ( j + 65 ) + ( i / 6 + 1 );
		else if ( chessBoard[i / 6][j] == null )
			fourteen = "              ";
		else
			fourteen = chessBoard[i / 6][j].getIcon(( whitesTurn ? i : 47 - i ) % 6);
		return fourteen;
	}

	
	public static void clear() throws InterruptedException, IOException
	{
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}
}