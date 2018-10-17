
public class Rook extends Piece {
	private String icon[] = {
			"\u2588\u2588  \u2588\u2588  \u2588\u2588",
			" \u2580\u2588\u2588\u2588\u2588\u2588\u2588\u2580 ",
			" \u2584\u2588\u2588\u2588\u2588\u2588\u2588\u2584 ",
			"\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588"
			};
	public Rook(boolean white) {
		super(white);
		this.name = "Rook";
		displayCharacter = 'r';
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
