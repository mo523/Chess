package chess;
@SuppressWarnings("serial")
public class Bishop extends Piece {
	private String icon[] = {
			"        ",
			"   \u2584\u2588\u2580\u2584 ",
			"  \u2580\u2588\u2584\u2588\u2588\u2580",
			"    \u2588\u2588  ",
			"  \u2584\u2588\u2588\u2588\u2588\u2584",
			"        "
			};
	private final boolean enPassantAble=false;
	public Bishop( boolean white) {
		super(white);
		this.name = "Bishop";
	}
	public String getIcon(int row){
		return "  " + icon[row] + "  ";
	}
	public boolean isWhite(){
		return white;
	}
	@Override
	public boolean canPieceMoveLikeThat(int fromRow,int fromCol, int toRow, int toCol, Piece[][] CB ) {
		int yDiff = Math.abs(toCol - fromCol);
		int xDiff = Math.abs(toRow - fromRow);
		if(yDiff == xDiff)
			return true;
		return false;
	}

	@Override
	public boolean noPieceInTheWay(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		int  XMoveDistance =(fromRow-toRow);
		int  YMoveDistance =(fromCol-toCol);
		boolean done = false;
		do
		{
			if(CB[fromCol-YMoveDistance][fromRow-XMoveDistance] != null && 
					!(fromCol-YMoveDistance == toCol && fromRow- XMoveDistance == toRow))
				return false;
			else {
				if (XMoveDistance>0)
					XMoveDistance--;
				
				if (XMoveDistance<0)
					XMoveDistance++;
				
				if (YMoveDistance>0)
					YMoveDistance--;
				
				if (YMoveDistance<0)
					YMoveDistance++;		
			}
			if(XMoveDistance == 0)
				done = true;
		}
		while (!done);
		return true;
	}
	public boolean isEnPassantAble() {
		return enPassantAble;
	}
}
