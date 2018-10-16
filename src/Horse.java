
public class Horse extends Piece {
	private String icon[] = { "            ", "     horse    ", "            ", "            ", "            "};
	
	public Horse(boolean white) {
		super( white);
		this.name = "Horse";
		displayCharacter = 'h';
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
