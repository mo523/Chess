
public class Queen extends Piece {
	private String icon[] = { "              ", "     queen    ", "              ", "              ", "              "};
	
	public Queen( boolean white) {
		super(white);
		this.name = "Queen";
		displayCharacter = 'q';
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
