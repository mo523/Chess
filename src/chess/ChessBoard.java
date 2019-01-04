package chess;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ChessBoard implements Serializable {
	/**
	 * 
	 */
	//there is an error without the uid
	private static final long serialVersionUID = 382550797992205908L;
	private Piece[][] chessBoard;
	private ArrayList<ArrayList<Piece>> pieces;
	private Piece blackKing;
	private Piece whiteKing;
	private boolean whitesTurn = true;
	private boolean debug;
	private boolean cpuGame;
	private boolean networkGame;
	private boolean playerTurn;
	private boolean useJansi;
	private Piece currKing;
	private boolean countingTurns = false;
	private int staleTurns = 0;
	private int fr = -1, fc = -1, tr = -1, tc = -1;
	private Network net;
	private boolean forceEnd = false;
	private String name;//for saved games
	
	// Constructor
	public ChessBoard(boolean debug, boolean cpuGame, boolean playerTurn, boolean networkGame, boolean useJansi) {
		this.debug = debug;
		this.cpuGame = cpuGame;
		this.playerTurn = playerTurn;
		this.networkGame = networkGame;
		this.useJansi = useJansi;
		setUpBoard();
		setUpArray();
	}

	// Board and Array setup
	private void setUpArray() {
		pieces = new ArrayList<>();
		pieces.add(new ArrayList<Piece>());
		pieces.add(new ArrayList<Piece>());
		for (Piece[] pa : chessBoard)
			for (Piece currPiece : pa)
				if (currPiece != null)
					pieces.get(currPiece.isWhite() ? 0 : 1).add(currPiece);
		System.out.println();
	}

	private void setUpBoard() {
		final boolean IS_WHITE = true;
		final boolean IS_BLACK = false;
		chessBoard = new Piece[8][8];

		chessBoard[1][0] = new Pawn(IS_WHITE, 1, 0);
		chessBoard[1][1] = new Pawn(IS_WHITE, 1, 1);
		chessBoard[1][2] = new Pawn(IS_WHITE, 1, 2);
		chessBoard[1][3] = new Pawn(IS_WHITE, 1, 3);
		chessBoard[1][4] = new Pawn(IS_WHITE, 1, 4);
		chessBoard[1][5] = new Pawn(IS_WHITE, 1, 5);
		chessBoard[1][6] = new Pawn(IS_WHITE, 1, 6);
		chessBoard[1][7] = new Pawn(IS_WHITE, 1, 7);
		chessBoard[6][0] = new Pawn(IS_BLACK, 6, 0);
		chessBoard[6][1] = new Pawn(IS_BLACK, 6, 1);
		chessBoard[6][2] = new Pawn(IS_BLACK, 6, 2);
		chessBoard[6][3] = new Pawn(IS_BLACK, 6, 3);
		chessBoard[6][4] = new Pawn(IS_BLACK, 6, 4);
		chessBoard[6][5] = new Pawn(IS_BLACK, 6, 5);
		chessBoard[6][6] = new Pawn(IS_BLACK, 6, 6);
		chessBoard[6][7] = new Pawn(IS_BLACK, 6, 7);
		chessBoard[0][0] = new Rook(IS_WHITE, 0, 0);
		chessBoard[0][1] = new Horse(IS_WHITE, 0, 1);
		chessBoard[0][2] = new Bishop(IS_WHITE, 0, 2);
		chessBoard[0][4] = new King(IS_WHITE, 0, 4);
		chessBoard[0][3] = new Queen(IS_WHITE, 0, 3);
		chessBoard[0][5] = new Bishop(IS_WHITE, 0, 5);
		chessBoard[0][6] = new Horse(IS_WHITE, 0, 6);
		chessBoard[0][7] = new Rook(IS_WHITE, 0, 7);
		chessBoard[7][0] = new Rook(IS_BLACK, 7, 0);
		chessBoard[7][1] = new Horse(IS_BLACK, 7, 1);
		chessBoard[7][2] = new Bishop(IS_BLACK, 7, 2);
		chessBoard[7][4] = new King(IS_BLACK, 7, 4);
		chessBoard[7][3] = new Queen(IS_BLACK, 7, 3);
		chessBoard[7][5] = new Bishop(IS_BLACK, 7, 5);
		chessBoard[7][6] = new Horse(IS_BLACK, 7, 6);
		chessBoard[7][7] = new Rook(IS_BLACK, 7, 7);
		whiteKing = chessBoard[0][4];
		blackKing = chessBoard[7][4];
		currKing = whiteKing;
//		chessBoard[3][1] = new Pawn(false, 3, 1);
//		chessBoard[2][4] = new Pawn(true, 2, 4);
//		chessBoard[5][0] = new Pawn(false,5,0);
		// Easy Checkmate test
//		chessBoard[5][4] = new King(true, 5, 4);
//		whiteKing = chessBoard[5][4];
//		blackKing = chessBoard[7][3];
//		currKing = whiteKing;
		// TESTS
//		chessBoard[3][1] = new Pawn(false, 3, 1);
//		chessBoard[3][0] = new Pawn(false, 3, 0);
//		chessBoard[6][0] = null;
//		chessBoard[6][6] = null;
//		chessBoard[5][6] = new Pawn(true, 5, 6);
	}

	// Public methods that effect the board
	public boolean performMove(int fromRow, int fromCol, int toRow, int toCol) {
		fr = fromRow;
		fc = fromCol;
		tr = toRow;
		tc = toCol;
		Piece currPiece = chessBoard[fromRow][fromCol];
		Piece killPiece = chessBoard[toRow][toCol];
		currPiece.setRowCol(toRow, toCol);
		pieces.get(whitesTurn ? 0 : 1).remove(killPiece);
		chessBoard[toRow][toCol] = currPiece;
		chessBoard[fromRow][fromCol] = null;
		if (currPiece instanceof Pawn)
			if (Math.abs(fromRow - toRow) == 2)
				currPiece.setEnPassant(true);
			else
				currPiece.setEnPassant(false);
		if (networkGame && playerTurn)
			try {
				net.sendServerData(fromRow, fromCol, toRow, toCol);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		playerTurn = !playerTurn;
		whitesTurn = !whitesTurn;
		currKing = whitesTurn ? whiteKing : blackKing;
		return (currPiece instanceof Pawn && toRow == (whitesTurn ? 0 : 7));
	}

	public void promotion(int row, int col, int choice) {
		chessBoard[row][col] = choice == 1 ? new Queen(whitesTurn, row, col)
				: choice == 2 ? new Bishop(whitesTurn, row, col)
						: choice == 3 ? new Rook(whitesTurn, row, col) : new Horse(whitesTurn, row, col);
	}

	// Public methods that effect the driver
	public void displayChoice() {
		if (debug)
			Display.debug(chessBoard);
		else
			Display.display(whitesTurn, useJansi, chessBoard, fr, fc, tr, tc);
	}

	public boolean gameStatus() {
		if (forceEnd)
			return false;
		if (inCheck())
			if (checkmate()) {
				System.out.println("Checkmate, You lost " + (whitesTurn ? "White." : "Black."));
				return false;
			} else
				System.out.println("\nWarning! Your king is in check!\n");
		if (staleMate()) {
			System.out.println("Game Over. Stalemate");
			return false;
		}
		if (countingTurns)
			System.out.println((18 - staleTurns++) + " turns left before stalemate");
		return true;
	}

	// private end game checks
	private boolean inCheck() {
		return currKing.inCheck(pieces, chessBoard);
	}

	private boolean checkmate() {
		return currKing.checkmate(pieces, chessBoard);
	}

	private boolean staleMate() {
		boolean white = oneKing(true);
		boolean black = oneKing(false);
		if (white && black)
			return true;
		if (white || black)
			return eighteenMoveStalemate();
		return cannotMove(); // if false - stalemate
	}

	private boolean cannotMove() {
		for (Piece piece : pieces.get(whitesTurn ? 0 : 1))
			for (int toRow = 0; toRow < 8; toRow++)
				for (int toCol = 0; toCol < 8; toCol++)
					if (piece.isLegalMove(toRow, toCol, pieces, chessBoard, currKing))
						return false;
		return true;
	}

	private boolean oneKing(boolean white) {
		return pieces.get(white ? 0 : 1).size() == 1;
	}

	private boolean eighteenMoveStalemate() {
		countingTurns = true;
		return staleTurns == 18;
	}

	// public move checks
	public boolean isValidPieceThere(int row, int col) {
		Piece currPiece = chessBoard[row][col];
		return currPiece != null && currPiece.isWhite() == whitesTurn;
	}

	public boolean canMoveThere(int fromRow, int fromCol, int toRow, int toCol) {
		// this null check makes sure you dont call isLegalMove on a null piece, which would result in a NullPointerException
		if(chessBoard[fromRow][fromCol] == null)
			return false;
		Piece currPiece = chessBoard[fromRow][fromCol];
		return currPiece.isLegalMove(toRow, toCol, pieces, chessBoard, currKing);
	}

	// public getters
	public boolean getCpuGame() {
		return cpuGame;
	}

	public boolean getNetGame() {
		return networkGame;
	}

	public boolean getTurn() {
		return playerTurn;
	}

	public boolean getWhite() {
		return whitesTurn;
	}

	public String getName(int row, int col) {
		String move = chessBoard[row][col].toString();
		move = move.substring(6, move.length() - 9);
		return move;
	}

	// public setters
	public void endNet() {
		networkGame = false;
	}

	public void reverseDebug() {
		debug = !debug;
	}

	public void reverseCpu() {
		cpuGame = !cpuGame;
	}

	public void setTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
	}

	// Network functionality
	public void setNet(Network net) {
		this.net = net;
		playerTurn = net.isServer();
	}

	public void netMove() {
		System.out.println("\nWaiting for other players move");
		try {
			int[] data = net.getClientData();
			performMove(data[0], data[1], data[2], data[3]);
		} catch (IOException ex) {
			System.out.println(ex);
			forceEnd = true;
		}
	}
/*
	// Saved game methods
	public void saveGame() throws FileNotFoundException, ClassNotFoundException, IOException {
		SaveGameFunctionality.saveGame(chessBoard, debug, whiteKing, blackKing, whitesTurn, cpuGame, playerTurn,
				countingTurns, staleTurns);
	}

	public void loadGame(SavedGame s) {
		debug = s.isDebug();
		whiteKing = s.getWhiteKing();
		blackKing = s.getBlackKing();
		whitesTurn = s.isWhitesTurn();
		chessBoard = s.getChessBoard();
		cpuGame = s.isCpuGame();
		playerTurn = s.isCpuTurn();
		countingTurns = s.isStartCountingTurns();
		staleTurns = s.getTurns();
	}*/


	public String getName() {
		return name;
	}
	/**
	 * This method is used to reference saved games
	 */
	public void setName(String name) {
		this.name = name;
	}

}