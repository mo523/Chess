
public class King extends Piece {
	private String icon[] = { "              ", "     king     ", "              ", "              ", "              "};
	
	public King( boolean white) {
		super( white);
		this.name = "King";
		displayCharacter = 'k';
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
