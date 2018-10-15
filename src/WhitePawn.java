
public class WhitePawn extends Piece {
	private boolean firstMove;
	public WhitePawn(String position, boolean white) {
		super(position, white);
		this.name = "WhitePawn";
		displayCharacter = 'P';
	}
}
