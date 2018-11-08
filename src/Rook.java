
public class Rook extends Piece {
	private String icon[] = {
			"            ",
			"    \u258C\u2588\u2590\u258C\u2588\u2590  " ,
			"    \u2580\u2588\u2588\u2588\u2588\u2580  ",
			"     \u2588\u2588\u2588\u2588   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ",
			"            "
			};
	public Rook(boolean white) {
		super(white);
		this.name = "Rook";
	}
	public String getIcon(int row)
	{
		return icon[row];
	}
	public boolean isWhite()
	{
		return white;
	}

	@Override
	public boolean canPieceMoveLikeThat(int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB ) {
		int yDiff = Math.abs(toRow - fromRow);
		int xDiff = Math.abs(toCol - fromCol);
		if(yDiff > 0 && xDiff > 0)
			return false;
		return true;
	}
	
	@Override
	public boolean noPieceInTheWay(int fromCol,int fromRow, int toCol, int toRow, Piece[][] CB) {
		int yDiff = toRow - fromRow;
		int xDiff = toCol - fromCol;
		if(xDiff == 0){
			if (yDiff > 0){
				for (int i = fromRow + 1; yDiff != 1; i++) {
					yDiff--;
					if (CB[i][fromCol] != null)
						return false;
				}
			}
			else{
				for (int i = fromRow - 1; yDiff != -1; i--) {
					yDiff++;
					if (CB[i][fromCol] != null)
						return false;
				}
			}
		}
		else{
			if (xDiff > 0){
				for (int i = fromCol + 1; xDiff != 1; i++) {
					xDiff--;
					if (CB[fromRow][i] != null)
						return false;
				}
			}
			else{
				for (int i = fromCol - 1; xDiff != -1; i--) {
					xDiff++;
					if (CB[fromRow][i] != null)
						return false;
				}
			}
		}
		return true;
	}
	/*
	public boolean inOneDirection(int diff, int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB){
		for (int i = 0; i <= diff; i++) {
			
		}
	}*/
 
}
