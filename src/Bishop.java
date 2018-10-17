public class Bishop extends Piece {
	private String icon[] = {
			"    \u2588\u2588    ",
			"  \u2580\u2580\u2588\u2588\u2580\u2580  ",
			"   \u2584\u2588\u2588\u2584   ",
			"  \u2588\u2588\u2588\u2588\u2588\u2588  "
			};
	
	public Bishop( boolean white) {
		super(white);
		this.name = "Bishop";
		displayCharacter = 'b';
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
