package chess;

@SuppressWarnings("serial")
public class Queen extends Piece {
	private String icon[] = {
			"            ",
			"     \u2584\u2590\u258C\u2584   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ",
			"     \u2590\u2588\u2588\u258C   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ",
			"   \u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580 "};
	private final boolean enPassantAble=false;
	public Queen( boolean white) {
		super(white);
		this.name = "Queen";
	}
	public String getIcon(int row){
		return icon[row];
	}
	public boolean isWhite(){
		return white;
	}
	
	@Override
	public boolean canPieceMoveLikeThat(int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB ) {
		Rook rook = new Rook(white);
		Bishop bishop = new Bishop(white);
		return (rook.canPieceMoveLikeThat(fromCol, fromRow, toCol, toRow, CB)||
				bishop.canPieceMoveLikeThat(fromCol, fromRow, toCol, toRow, CB));
	}

	@Override
	public boolean noPieceInTheWay(int fromCol,
			int fromRow, int toCol, int toRow,
			Piece[][] CB) {
		
		Rook rook = new Rook(white);
		Bishop bishop = new Bishop(white);
		
		int yDiff = Math.abs(toRow - fromRow);
		int xDiff = Math.abs(toCol - fromCol);
		if(yDiff == xDiff)
			return bishop.noPieceInTheWay(fromCol, fromRow, toCol, toRow, CB);
		else
			return rook.noPieceInTheWay(fromCol, fromRow, toCol, toRow, CB);		
	}
	public boolean isEnPassantAble() {
		return enPassantAble;
	}
}
