import java.io.Serializable;

public class SavedGame implements Serializable{
	Piece[][] chessBoard;
	private  boolean debug;
	private  Piece whiteKing, blackKing;
	private  boolean whitesTurn = true;
	private  boolean cpuGame;
	private  boolean cpuWhite;
	private  boolean startCountingTurns;
	private  int turns = 0;
	
	public SavedGame(Piece[][] chessBoard, boolean debug, Piece whiteKing, Piece blackKing, boolean whitesTurn,
			boolean cpuGame, boolean cpuWhite, boolean startCountingTurns, int turns) {
		super();
		this.chessBoard = chessBoard;
		this.debug = debug;
		this.whiteKing = whiteKing;
		this.blackKing = blackKing;
		this.whitesTurn = whitesTurn;
		this.cpuGame = cpuGame;
		this.cpuWhite = cpuWhite;
		this.startCountingTurns = startCountingTurns;
		this.turns = turns;
	}
	public Piece[][] getChessBoard() {
		return chessBoard;
	}

	public boolean isDebug() {
		return debug;
	}

	public Piece getWhiteKing() {
		return whiteKing;
	}

	public Piece getBlackKing() {
		return blackKing;
	}

	public boolean isWhitesTurn() {
		return whitesTurn;
	}

	public boolean isCpuGame() {
		return cpuGame;
	}

	public boolean isCpuWhite() {
		return cpuWhite;
	}

	public boolean isStartCountingTurns() {
		return startCountingTurns;
	}

	public int getTurns() {
		return turns;
	}

}
