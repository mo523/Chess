import java.util.Scanner;


public class ChessDriver {
	
	static Scanner kyb = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		boolean white = true;
		//boolean black = !white;
		System.out.println("Player 1 (white), what is your name?");
		Player p1 = new Player(white, kyb.next());
		System.out.println("Player 2 (black), what is your name?");
		Player p2 = new Player(!white, kyb.next());
		
		playGame(p1, p2);
		
		
	}
	
	public static void playGame(Player p1, Player p2){
		
		do{
			eachPlayersMove(p1);
			eachPlayersMove(p2);
		}while(p1.isNotInCheckMate() && p2.isNotInCheckMate());
	}
	
	public static void eachPlayersMove(Player p){
		boolean legalMove = true;
		displayBoard();
		
		System.out.println(p.getName() + ", what is your move?");
		do{
			if(!legalMove){
				System.out.println("Illegal move, try again");
			}
			legalMove = p.movePiece(convertPositionToNumbers(kyb.next()),convertPositionToNumbers( kyb.next()));
		}while(!legalMove);
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
