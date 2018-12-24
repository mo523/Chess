package chess;
import java.io.Serializable;

@SuppressWarnings("serial")
public class SavedGame implements Serializable{
	Piece[][] chessBoard;
	private boolean debug;
	private Piece whiteKing, blackKing;
	private boolean whitesTurn = true;
	private boolean cpuGame;
	private boolean cpuTurn;
	private boolean startCountingTurns;
	private int turns = 0;
	private String name;
	public SavedGame(Piece[][] chessBoard, boolean debug, Piece whiteKing, Piece blackKing, boolean whitesTurn,
			boolean cpuGame, boolean cpuTurn, boolean startCountingTurns, int turns, String name) {
		super();
		this.chessBoard = chessBoard;
		this.debug = debug;
		this.whiteKing = whiteKing;
		this.blackKing = blackKing;
		this.whitesTurn = whitesTurn;
		this.cpuGame = cpuGame;
		this.cpuTurn = cpuTurn;
		this.startCountingTurns = startCountingTurns;
		this.turns = turns;
		this.name = name;
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

	public boolean isCpuTurn() {
		return cpuTurn;
	}

	public boolean isStartCountingTurns() {
		return startCountingTurns;
	}

	public int getTurns() {
		return turns;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
