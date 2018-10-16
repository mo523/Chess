
public class Pawn extends Piece
{
	private String icon[] = { "            ", "     pawn   ", "            ", "            ", "            "};
	public Pawn( boolean white )
	{
		super( white);
		
	}
	public String[] getIcon()
	{
		return icon;
	}

}
