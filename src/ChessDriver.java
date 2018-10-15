import java.util.Scanner;


public class ChessDriver {

	public static void main(String[] args) {
		Scanner kyb = new Scanner(System.in);
		boolean white = true;
		//boolean black = !white;
		System.out.println("Player 1 (white), what is your name?");
		Player p1 = new Player(white, kyb.next());
		System.out.println("Player 2 (black), what is your name?");
		Player p2 = new Player(!white, kyb.next());
		
		//displayBoard();
		for(int i = 0; i < 16; i++){
			System.out.println(p1.getPieces()[i].getName());
			System.out.println("white? "+p1.getPieces()[i].isWhite());
			System.out.println(p1.getPieces()[i].getDisplayCharacter());
			System.out.println(p1.getPieces()[i].getPosition());
		}
		for(int i = 0; i < 16; i++){
			System.out.println(p2.getPieces()[i].getName());
			System.out.println("black? " + p2.getPieces()[i].isWhite());
			System.out.println(p2.getPieces()[i].getDisplayCharacter());
			System.out.println(p2.getPieces()[i].getPosition());
		}
	}

}
