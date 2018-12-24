package chess;

@SuppressWarnings("serial")
public class Pawn extends Piece {
	private String icon[] = { "            ", "      \u2584\u2584    ", "     \u2580\u2588\u2588\u2580   ",
			"    \u2584\u2588\u2588\u2588\u2588\u2584  ", "    \u2580\u2580\u2580\u2580\u2580\u2580  ",
			"            ", };
	private int verticalMoveMax;
	private boolean firstMove = true;
	private boolean enPassantAble;
	private boolean enPassantMove = false;

	public Pawn(boolean white) {
		super(white);
		this.name = "Pawn";
		verticalMoveMax = white ? 1 : -1;
	}

	public String getIcon(int row) {
		return icon[row];
	}

	public boolean isWhite() {
		return white;
	}

	@Override
	public boolean canPieceMoveLikeThat(int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB) {
		int yDiff = toRow - fromRow;
		int xDiff = Math.abs(toCol - fromCol);
		int tempY;
		
		if (toRow==2||toRow==5)
		  enPassantMove(fromCol, fromRow, toCol, toRow, CB);


		if ((xDiff > 1 && !enPassantMove) || (xDiff != 1 && enPassantMove))
			return false;
		if (xDiff == 1 && (Math.abs(yDiff) != 1)&&!enPassantMove)
			return false;
		if (xDiff == 1 && CB[toRow][toCol] == null &&!enPassantMove)
			return false;
		
		if (CB[toRow][toCol] != null)
		if ((xDiff == 1 && CB[toRow][toCol].isWhite() == this.isWhite()))
			return false;
		if (xDiff == 0 && CB[toRow][toCol] != null)
			return false;

		tempY = verticalMoveMax * (firstMove ? 2 : 1);

		if (yDiff == tempY || yDiff == verticalMoveMax) {
			if (ChessDriver.isMovingPiece())
				firstMove = false;
			return true;
		}
		
		return false;
		
	}

	@Override
	protected Pawn clone() throws CloneNotSupportedException {
		Pawn p = new Pawn(this.white);
		p.enPassantAble = this.enPassantAble;
		p.enPassantMove = this.enPassantMove;
		p.firstMove = this.firstMove;
		return p;
	}

	@Override
	public boolean noPieceInTheWay(int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB) {
		// this is taken care of by canPieceMoveLikeThat
		return true;
	}

	public void setFirstMove(boolean first) {
		this.firstMove = first;
	}

	public boolean getFirstMove() {
		return this.firstMove;
	}

	public boolean isEnPassantAble() {
		return enPassantAble;
	}

	public void changeEnPassantAble(boolean change) {
		enPassantAble = change;
	}

	public void enPassantMove(int fromCol, int fromRow, int toCol, int toRow, Piece[][] CB) {
		if (CB[fromRow][fromCol] != null) {
			if (CB[4][toCol] != null)
				if ((CB[fromRow][fromCol].isWhite() && CB[4][toCol].isEnPassantAble()))
					enPassantMove= true;
			if (CB[3][toCol] != null)
				if ((!CB[fromRow][fromCol].isWhite() && CB[3][toCol].isEnPassantAble()))
					enPassantMove= true;
		}
	
	}
	
	public boolean enPassantMove() {
		return enPassantMove;
	}
}
