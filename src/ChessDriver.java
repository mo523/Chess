import java.io.IOException;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;

public class ChessDriver
{

	static Scanner kyb = new Scanner(System.in);
	static final boolean IS_WHITE = true;// this is to make what color the pieces are more clear (rather than using true
											// and false)
	static final boolean IS_BLACK = false;// this is to make what color the pieces are more clear (rather than using// true and false)
	static boolean debug;
	
	public static void main( String[] args ) throws InterruptedException, IOException
	{
		System.out.println("(R)egular mode\n(D)ebug mode");
		debug = kyb.nextLine().toUpperCase().equals("D") ? true : false;
		
		AnsiConsole.systemInstall();
		Piece CB[][] = new Piece[8][8];
		setUpPieces(CB);
		/*
		 * System.out.println("Player 1 (white), what is your name?");// String p1 =
		 * kyb.nextLine(); System.out.println("Player 2 (black), what is your name?");
		 * String p2 = kyb.nextLine();
		 */
		playGame("p1", "p2", CB);// remember to take out the literal p1 and p2
		kyb.close();
		AnsiConsole.systemUninstall();
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
		String from = "", to = "";
		boolean legalMoveInput = true;
		final boolean IS_FROM = true;
		final boolean IS_TO = false;
		int from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate;
		do
		{
			if ( !canPieceMoveThereBasedOnAllItsRules )
				System.out.println("Bad Move, try again");
			do
			{
				//clear();
				if (debug)
					displayDebug(CB);
				else
					display(CB);
				if ( !legalMoveInput )
					System.out.println(name + ", You do not have a piece there\nPlease try again;");
				System.out.println(name + ", Which piece would you like to move?");
				from = getCorrect2CharPosition();
				from_X_Coordinate = from.charAt(0) - 97;
				from_Y_Coordinate = from.charAt(1) - 49;

				legalMoveInput = isValidPieceThere(from, from_X_Coordinate, from_Y_Coordinate, CB, localWhite, IS_FROM);
			} while ( !legalMoveInput );
			do
			{
				
				if ( !legalMoveInput )
					System.out.println("Illegal complete move, try again");
				System.out.println(name + ", Where would you like to move your piece to?");
				do {
					to = getCorrect2CharPosition();
					if(to.equalsIgnoreCase(from))
						System.out.println("Can't move to same place. Try again.");
				}while(to.equalsIgnoreCase(from));
				to_X_Coordinate = to.charAt(0) - 97;
				to_Y_Coordinate = to.charAt(1) - 49;
				legalMoveInput = isValidPieceThere(from, from_X_Coordinate, from_Y_Coordinate, CB, localWhite, IS_TO);
			} while ( !legalMoveInput );
			
			canPieceMoveThereBasedOnAllItsRules = canMoveThere( from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB );
			
		} while ( !canPieceMoveThereBasedOnAllItsRules );
		performMove(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);
	}

	public static void performMove( int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate,
			int to_Y_Coordinate, Piece[][] CB ){
		CB[to_Y_Coordinate][to_X_Coordinate] = CB[from_Y_Coordinate][from_X_Coordinate];
		CB[from_Y_Coordinate][from_X_Coordinate] = null;
	}
	
	public static String getCorrect2CharPosition(){
		String position = makeSureStringIs2Chars();
		position = correctChars(position);
		return position;
	}
	
	public static String makeSureStringIs2Chars(){
		String position = "";
		do{
			position = kyb.next().toLowerCase();
			if(position.length() != 2)
				System.out.println("Position must be 2 characters");
		}while(position.length() != 2);
		
		return position;
	}
	public static String correctChars(String position){
		while(position.charAt(0) < 97 || position.charAt(0) > 104 || position.charAt(1) < 49 || position.charAt(1) > 56 ){
			System.out.println("Invalid position entry. It must be a [a-h] then [1-8]. Try Again");
			position = kyb.next().toLowerCase();
		}
		return position;
	}
	// call to piece method if valid move, add do while loop in last method
	public static boolean canMoveThere( int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate,
			int to_Y_Coordinate, Piece[][] CB )
	{
		return CB[from_Y_Coordinate][from_X_Coordinate].isLegalMove(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);
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

	//needs work
	public static boolean notInCheckMate( Piece king )
	{
		return true;
	}

	public static void setUpPieces( Piece[][] CB )
	{
		// for tests only
		//	CB[2][4] = new King(IS_WHITE);
		//	CB[5][1] = new Bishop(IS_BLACK);
		//	CB[3][3] = new Pawn(IS_WHITE);
		// for tests only
		
		
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
		if ( i % 6 == 5 )
			if (cb[i / 6][j] == null)
				fourteen = "            " + (char) ( j + 65 ) + ( i / 6 + 1 );
			else
				fourteen = cb[i / 6][j].getIcon(i % 6) + (char) ( j + 65 ) + ( i / 6 + 1 );
		else if ( cb[i / 6][j] == null)
			fourteen = "              ";
		else
			fourteen = cb[i / 6][j].getIcon(i % 6 );
		return fourteen;
	}

	public static void displayDebug( Piece[][] CB ){
		System.out.println("  A  B  C  D  E  F  G  H");
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (CB[i][j] == null)
					System.out.print((j == 0 ? (i+1)  + " ": "" ) + "nn ");		
				else
					System.out.print((j == 0 ? (i+1) + " " : "" ) + (CB[i][j].white ? "w" : "b") + CB[i][j].toString().charAt(0) + " ");										
			}
			System.out.println();
		}
		System.out.println();
		
	}
	public static void display( Piece[][] CB )
	{
		String letters = "        A             B             C             D             E             F             G             H        ";
		String reset = "\u001B[0m";
		//String test = "\u001B[38;2;255;135;0m";
		String bxWhite = "\u001B[107m";
		String bgWhite = "\u001B[47m";
		String bgBlack = "\u001B[100m";
		String fgWhite = "\u001B[97m";
		String fgBlack = "\u001B[30m";
		String fgBlue = "\u001B[34m";
		System.out.println(bxWhite + fgBlack + letters + " " + reset);
		for ( int i = 0; i < 48; i++ )
		{
			boolean numRow = i % 6 + 1 == 3;
			System.out.print(bxWhite + fgBlack + (numRow? i/6+1 + " " : "  ") + reset);

			for ( int j = 0; j < 8; j++ )
			{
				Boolean isWhite = CB[i / 6][j] != null ? CB[i / 6][j].isWhite() : null;
				boolean ijTheSame = i / 6 % 2 == j % 2;
				boolean isNull = CB[i / 6][j] == null;
				System.out.print(( ijTheSame ? bgWhite : bgBlack ) + ( isNull ? fgBlue : isWhite ? fgWhite : fgBlack )
						+ PieceSection(i, j, CB) + reset);
			}
			System.out.print(bxWhite + fgBlack + (numRow? " " + (i/6+1) : "  ") + reset);
			System.out.println();
		}
		System.out.println(bxWhite + fgBlack + " " + letters + reset);
	}

	public static void clear() throws InterruptedException, IOException
	{
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}  
}



/*public static boolean doesntLeaveKingInCheck(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB){
int xCoordinate = 0;
int yCoordinate = 0;
boolean done = false;
for (int i = 0; i < 8; i++){
	for (int j = 0; j < 8; j++){
		if(CB[i][j] instanceof King && CB[i][j].isWhite() == CB[from_Y_Coordinate][from_X_Coordinate].isWhite()){
			yCoordinate = i;
			xCoordinate = j;
			done = true;
			break;
		}
	}
	if(done)
		break;
}

for (int i = 0; i < 8; i++)
	for (int j = 0; j < 8; j++)
		if(moveCheckForCheck(j, i, xCoordinate, yCoordinate, CB))
			return false;
return true;

}
private static boolean moveCheckForCheck(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB){
if(CB[from_Y_Coordinate][from_X_Coordinate] != null)
	return CB[from_Y_Coordinate][from_X_Coordinate].canPieceMoveLikeThat(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
		&& CB[from_Y_Coordinate][from_X_Coordinate].willNotKillSameColor(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB)
		&& CB[from_Y_Coordinate][from_X_Coordinate].noPieceInTheWay(from_X_Coordinate, from_Y_Coordinate, to_X_Coordinate, to_Y_Coordinate, CB);
return false;
}*/
