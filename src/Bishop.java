public class Bishop extends Piece {
	private String icon[] = { "              ", "    bishop    ", "              ", "              ", "              "};
	
	public Bishop( boolean white) {
		super(white);
		this.name = "Bishop";
		displayCharacter = 'b';
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
