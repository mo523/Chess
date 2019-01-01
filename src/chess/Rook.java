package chess;

@SuppressWarnings("serial")
public class Rook extends Piece {
	private String icon[] = {
			"            ",
			"    \u258C\u2588\u2590\u258C\u2588\u2590  " ,
			"    \u2580\u2588\u2588\u2588\u2588\u2580  ",
			"     \u2588\u2588\u2588\u2588   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ",
			"            "
			};
	private final boolean enPassantAble=false;
	public Rook(boolean white) {
		super(white);
		this.name = "Rook";
	}
	public String getIcon(int row){
		return icon[row];
	}
	public boolean isWhite(){
		return white;
	}

	@Override
	public boolean canPieceMoveLikeThat(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB ) {
		int yDiff = Math.abs(toCol - fromCol);
		int xDiff = Math.abs(toRow - fromRow);
		if(yDiff > 0 && xDiff > 0)
			return false;
		return true;
	}
	
	@Override
	public boolean noPieceInTheWay(int fromRow,int fromCol, int toRow, int toCol, Piece[][] CB) {
		int yDiff = toCol - fromCol;
		int xDiff = toRow - fromRow;
		if(xDiff == 0){
			if (yDiff > 0){
				for (int i = fromCol + 1; yDiff != 1; i++) {
					yDiff--;
					if (CB[i][fromRow] != null)
						return false;
				}
			}
			else{
				for (int i = fromCol - 1; yDiff != -1; i--) {
					yDiff++;
					if (CB[i][fromRow] != null)
						return false;
				}
			}
		}
		else{
			if (xDiff > 0){
				for (int i = fromRow + 1; xDiff != 1; i++) {
					xDiff--;
					if (CB[fromCol][i] != null)
						return false;
				}
			}
			else{
				for (int i = fromRow - 1; xDiff != -1; i--) {
					xDiff++;
					if (CB[fromCol][i] != null)
						return false;
				}
			}
		}
		return true;
	}
	public boolean isEnPassantAble() {
		return enPassantAble;
	}
	/*
	public boolean inOneDirection(int diff, int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB){
		for (int i = 0; i <= diff; i++) {
			
		}
	}*/
 
}
