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
			legalMove = p.movePiece(kyb.next(), kyb.next());
		}while(!legalMove);
	}
	public static void displayBoard(){
		
	}

}
