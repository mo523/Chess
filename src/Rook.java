
public class Rook extends Piece {
	private String icon[] = {
			"          ",
			" \u258C \u2588  \u2588 \u2590 " ,
			" \u2580\u2588\u2588\u2588\u2588\u2588\u2588\u2580 ",
			"  \u2588\u2588\u2588\u2588\u2588\u2588  ",
			" \u2584\u2588\u2588\u2588\u2588\u2588\u2588\u2584 ",
			"        "
			};
	public Rook(boolean white) {
		super(white);
		this.name = "Rook";
		displayCharacter = 'r';
	}
	public String getIcon(int row)
	{
		return  "  " + icon[row] + "  ";
	}
	public boolean isWhite()
	{
		return white;
	}

	@Override
	public boolean canPieceMoveLikeThat(int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB ) {
		int yDiff = Math.abs(to_Y_Coordinate - from_Y_Coordinate);
		int xDiff = Math.abs(to_X_Coordinate - from_X_Coordinate);
		if(yDiff > 0 && xDiff > 0)
			return false;
		return true;
	}
	
	@Override
	public boolean noPieceInTheWay(int from_X_Coordinate,int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB) {
		int yDiff = to_Y_Coordinate - from_Y_Coordinate;
		int xDiff = to_X_Coordinate - from_X_Coordinate;
		if(xDiff == 0){
			if (yDiff > 0){
				for (int i = from_Y_Coordinate + 1; yDiff != 0; i++) {
					yDiff--;
					if (CB[i][from_X_Coordinate] != null)
						return false;
				}
			}
			else{
				for (int i = from_Y_Coordinate - 1; yDiff != 0; i--) {
					yDiff++;
					if (CB[i][from_X_Coordinate] != null)
						return false;
				}
			}
		}
		else{
			if (xDiff > 0){
				for (int i = from_X_Coordinate + 1; xDiff != 0; i++) {
					xDiff--;
					if (CB[from_Y_Coordinate][i] != null)
						return false;
				}
			}
			else{
				for (int i = from_X_Coordinate - 1; xDiff != 0; i--) {
					xDiff++;
					if (CB[from_Y_Coordinate][i] != null)
						return false;
				}
			}
		}
		return true;
	}
	/*
	public boolean inOneDirection(int diff, int from_X_Coordinate, int from_Y_Coordinate, int to_X_Coordinate, int to_Y_Coordinate, Piece[][] CB){
		for (int i = 0; i <= diff; i++) {
			
		}
	}*/
 
}
