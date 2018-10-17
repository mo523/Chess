
public class Horse extends Piece {
	private String icon[] = {
			"   \u2584\u2588\u2588\u2584\u2584  ",
			"  \u2588\u2588\u2588\u2580\u2580\u2588\u2588 ",
			"  \u2580\u2588\u2588\u2588\u2584   ",
			"  \u2584\u2588\u2588\u2588\u2588\u2584  "
			};
	
	public Horse(boolean white) {
		super( white);
		this.name = "Horse";
		displayCharacter = 'h';
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
