import java.util.Scanner;


public class ChessDriver {
	
	static Scanner kyb = new Scanner(System.in);
	static final  int Max_Num_Of_Pieces = 16;
	public static void main(String[] args) {
		
		boolean white = true;
		System.out.println("Player 1 (white), what is your name?");
		Player p1 = new Player(white, kyb.next());
		System.out.println("Player 2 (black), what is your name?");
		Player p2 = new Player(!white, kyb.next());
		
		playGame(p1, p2);
		
		
	}
	
	public static void playGame(Player p1, Player p2){
		
		do{
			if(p1.isNotInCheckMate())
				eachPlayersMove(p1, p2);
			if(p2.isNotInCheckMate())
				eachPlayersMove(p2, p1);
		}while(p1.isNotInCheckMate() && p2.isNotInCheckMate());
	}
	
	public static void eachPlayersMove(Player movingPlayer, Player passivePlayer){
		boolean legalMove = true;
		displayBoard();
		
		System.out.println(movingPlayer.getName() + ", what is your move?");
		do{
			if(!legalMove){
				System.out.println("Illegal move, try again");
			}
			
			String from = convertPositionToNumbers(kyb.next());
			String to = convertPositionToNumbers(kyb.next());
			legalMove = movingPlayer.movePiece(from, to);
			
			if(legalMove)
				killPiece(passivePlayer, to);
			
		}while(!legalMove);
	}
	
	public static void killPiece(Player p, String to) {
		for (int i = 0; i < Max_Num_Of_Pieces; i++) {
			if(p.getPieces()[i].getPosition().equalsIgnoreCase(to)) {
				p.getPieces()[i] = null;
				break;
			}	
		}
	}
	public static void displayBoard(){
		
	}
	public static String convertPositionToNumbers(String move){
		String positionInNumbers = "";
		switch(move.charAt(0)){
		case 'a' :
			positionInNumbers += "0";
			break;
		case 'b' :
			positionInNumbers += "1";
			break;
		case 'c' :
			positionInNumbers += "2";
			break;
		case 'd' :
			positionInNumbers += "3";
			break;
		case 'e' :
			positionInNumbers += "4";
			break;
		case 'f' :
			positionInNumbers += "5";
			break;
		case 'g' :
			positionInNumbers += "6";
			break;
		case 'h' :
			positionInNumbers += "7";
			break;
		}
		positionInNumbers += Integer.toString((Integer.parseInt(move.substring(1)) - 1));
		return positionInNumbers;
	}

}
