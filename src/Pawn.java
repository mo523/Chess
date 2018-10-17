
public class Pawn extends Piece
{
	private String icon[] = {
			"    \u2584\u2584    ",
			"   \u2580\u2588\u2588\u2580   ",
			"  \u2584\u2588\u2588\u2588\u2588\u2584  ",
			"  \u2580\u2580\u2580\u2580\u2580\u2580  "
			};
	public Pawn( boolean white )
	{
		super( white);
	}
	public String getIcon(int row)
	{
		return "  " + icon[row] + "  ";
	}
	public boolean isWhite()
	{
		return white;
	}

}
