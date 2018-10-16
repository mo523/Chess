
public class Rook extends Piece {
	private String icon[] = { "            ", "     rook     ", "            ", "            ", "            "};
	public Rook(boolean white) {
		super(white);
		this.name = "Rook";
		displayCharacter = 'r';
	}
	public String getIcon(int row)
	{
		return icon[row];
	}
	public boolean isWhite()
	{
		return white;
	}
}
