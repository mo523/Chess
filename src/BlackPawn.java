
public class BlackPawn extends Piece {
	private boolean firstMove;
	public BlackPawn(String position, boolean white) {
		super(position, white);
		this.name = "BlackPawn";
		displayCharacter = 'p';
	}
}
