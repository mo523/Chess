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
	public boolean canPieceMoveLikeThat(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		int yDiff = toCol - fromCol;
		int xDiff = Math.abs(toRow - fromRow);
		int tempY;
		
		if (toCol==2||toCol==5)
		  enPassantMove(fromRow, fromCol, toRow, toCol, CB);


		if ((xDiff > 1 && !enPassantMove) || (xDiff != 1 && enPassantMove))
			return false;
		if (xDiff == 1 && (Math.abs(yDiff) != 1)&&!enPassantMove)
			return false;
		if (xDiff == 1 && CB[toCol][toRow] == null &&!enPassantMove)
			return false;
		
		if (CB[toCol][toRow] != null)
			if ((xDiff == 1 && CB[toCol][toRow].isWhite() == this.isWhite()))
				return false;
		if (xDiff == 0 && CB[toCol][toRow] != null)
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
	public boolean noPieceInTheWay(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
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

	public void enPassantMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] CB) {
		if (CB[fromCol][fromRow] != null) {
			if (CB[4][toRow] != null)
				if ((CB[fromCol][fromRow].isWhite() && CB[4][toRow].isEnPassantAble()))
					enPassantMove= true;
			if (CB[3][toRow] != null)
				if ((!CB[fromCol][fromRow].isWhite() && CB[3][toRow].isEnPassantAble()))
					enPassantMove= true;
		}
	
	}
	
	public boolean enPassantMove() {
		return enPassantMove;
	}
}
