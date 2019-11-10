package chess;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ChessBoard implements Serializable
{
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
	private Piece currKing;
	private boolean countingTurns = false;
	private int staleTurns = 0;
	private int fr = -1, fc = -1, tr = -1, tc = -1;
	private Network network;
	private boolean forceEnd = false;
	private String name;// for saved games
	private AI ai;

	// Constructor
	public ChessBoard(boolean debug, int AIlevel, boolean playerTurn, Network network, boolean random)
	{
		this.debug = debug;
		this.playerTurn = playerTurn;
		this.networkGame = network != null;
		this.network = network;
		chessBoard = new Piece[8][8];
		if (random)
			setUpRandomBoard();
		else
			setUpBoard();
		setUpArray();
		if (AIlevel > 0)
		{
			cpuGame = true;
			ai = new AI(AIlevel, this);
		}
	}

	public ChessBoard(Boolean debug, boolean white, int ail, boolean playerTurn, int stale, int[] ft, int[][] pieces)
	{
		this.debug = debug;
		this.whitesTurn = white;
		this.playerTurn = playerTurn;
		this.networkGame = false;
		chessBoard = new Piece[8][8];
		fr = ft[0];
		fc = ft[1];
		tr = ft[2];
		tc = ft[3];
		for (int i = 0; i < pieces.length; i++)
		{
			boolean w = pieces[i][0] == 1;
			int p = pieces[i][1];
			int r = pieces[i][2];
			int c = pieces[i][3];
			Piece piece;

			switch (p)
			{
				case 0:
					piece = new Pawn(w, r, c);
					break;
				case 1:
					piece = new Rook(w, r, c);
					break;
				case 2:
					piece = new Bishop(w, r, c);
					break;
				case 3:
					piece = new Horse(w, r, c);
					break;
				case 4:
					piece = new Queen(w, r, c);
					break;
				case 5:
					piece = new King(w, r, c);
					if (w)
						whiteKing = piece;
					else
						blackKing = piece;
					break;

				default:
					piece = null;
					break;
			}

			chessBoard[r][c] = piece;
		}
		currKing = whitesTurn ? whiteKing : blackKing;
		setUpArray();
		if (ail > 0)
		{
			cpuGame = true;
			ai = new AI(ail, this);
		}
	}

	// Board and Array setup
	private void setUpArray()
	{
		pieces = new ArrayList<>();
		pieces.add(new ArrayList<Piece>());
		pieces.add(new ArrayList<Piece>());
		for (Piece[] pa : chessBoard)
			for (Piece currPiece : pa)
				if (currPiece != null)
					pieces.get(currPiece.isWhite() ? 0 : 1).add(currPiece);
		System.out.println();
	}

	private void setUpBoard()
	{
		final boolean WHITE = true;
		final boolean BLACK = false;

		for (int i = 0; i < 8; i++)
			chessBoard[1][i] = new Pawn(WHITE, 1, i);
		for (int i = 0; i < 8; i++)
			chessBoard[6][i] = new Pawn(BLACK, 6, i);
		chessBoard[0][0] = new Rook(WHITE, 0, 0);
		chessBoard[0][1] = new Horse(WHITE, 0, 1);
		chessBoard[0][2] = new Bishop(WHITE, 0, 2);
		chessBoard[0][4] = new King(WHITE, 0, 4);
		chessBoard[0][3] = new Queen(WHITE, 0, 3);
		chessBoard[0][5] = new Bishop(WHITE, 0, 5);
		chessBoard[0][6] = new Horse(WHITE, 0, 6);
		chessBoard[0][7] = new Rook(WHITE, 0, 7);
		chessBoard[7][0] = new Rook(BLACK, 7, 0);
		chessBoard[7][1] = new Horse(BLACK, 7, 1);
		chessBoard[7][2] = new Bishop(BLACK, 7, 2);
		chessBoard[7][4] = new King(BLACK, 7, 4);
		chessBoard[7][3] = new Queen(BLACK, 7, 3);
		chessBoard[7][5] = new Bishop(BLACK, 7, 5);
		chessBoard[7][6] = new Horse(BLACK, 7, 6);
		chessBoard[7][7] = new Rook(BLACK, 7, 7);
		whiteKing = chessBoard[0][4];
		blackKing = chessBoard[7][4];
		currKing = whiteKing;
	}

	private void setUpRandomBoard()
	{
		Random ran = new Random();
		int row = ran.nextInt(2);
		int col = ran.nextInt(8);
		chessBoard[row][col] = new King(true, row, col);
		chessBoard[7 - row][col] = new King(false, 7 - row, col);
		whiteKing = chessBoard[row][col];
		blackKing = chessBoard[7 - row][col];
		currKing = whiteKing;
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 8; j++)
				if (chessBoard[i][j] == null)
				{
					Piece piece;
					int num = ran.nextInt(10);
					switch (num)
					{
						case 1:
						case 7:
							piece = new Bishop(true, i, j);
							break;
						case 2:
						case 8:
							piece = new Rook(true, i, j);
							break;
						case 3:
						case 6:
							piece = new Horse(true, i, j);
							break;
						case 5:
							piece = new Queen(true, i, j);
							break;
						default:
							piece = new Pawn(true, i, j);
							break;
					}
					chessBoard[i][j] = piece;
				}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 8; j++)
			{
				int r = 7 - i;
				Piece blackPiece;
				Piece whitePiece = chessBoard[i][j];
				if (whitePiece instanceof Pawn)
					blackPiece = new Pawn(false, r, j);
				else if (whitePiece instanceof Horse)
					blackPiece = new Horse(false, r, j);
				else if (whitePiece instanceof Rook)
					blackPiece = new Rook(false, r, j);
				else if (whitePiece instanceof Bishop)
					blackPiece = new Bishop(false, r, j);
				else if (whitePiece instanceof Queen)
					blackPiece = new Queen(false, r, j);
				else
					continue;
				chessBoard[r][j] = blackPiece;
			}
	}

	// Public methods that effect the board
	public boolean performMove(int fromRow, int fromCol, int toRow, int toCol)
	{
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
				((Pawn) currPiece).setEnPassant(true);
			else
				((Pawn) currPiece).setEnPassant(false);
		if (networkGame && playerTurn)
			try
			{
				network.sendMove(fromRow, fromCol, toRow, toCol);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		playerTurn = !playerTurn;
		whitesTurn = !whitesTurn;
		currKing = whitesTurn ? whiteKing : blackKing;
		return (currPiece instanceof Pawn && toRow == (whitesTurn ? 0 : 7));
	}

	public void promotion(int row, int col, int choice)
	{
		chessBoard[row][col] = choice == 1 ? new Queen(whitesTurn, row, col)
				: choice == 2 ? new Bishop(whitesTurn, row, col)
						: choice == 3 ? new Rook(whitesTurn, row, col) : new Horse(whitesTurn, row, col);
	}

	public void AIMove()
	{
		ai.doAIMove();
	}

	// Public methods that effect the driver
	public void displayChoice()
	{
		if (debug)
			Display.debug(chessBoard);
		else
			Display.display(whitesTurn, chessBoard, fr, fc, tr, tc);
	}

	public boolean gameStatus()
	{
		if (forceEnd)
			return false;
		if (inCheck())
			if (checkmate())
			{
				System.out.println("Checkmate, You lost " + (whitesTurn ? "White." : "Black."));
				return false;
			}
			else
				System.out.println("\nWarning! Your king is in check!\n");
		if (staleMate())
		{
			System.out.println("Game Over. Stalemate");
			return false;
		}
		if (countingTurns)
			System.out.println((18 - staleTurns++) + " turns left before stalemate");
		return true;
	}

	// private end game checks
	private boolean inCheck()
	{
		return ((King) currKing).inCheck(pieces, chessBoard);
	}

	private boolean checkmate()
	{
		return ((King) currKing).checkmate(pieces, chessBoard);
	}

	private boolean staleMate()
	{
		boolean white = oneKing(true);
		boolean black = oneKing(false);
		if (white && black)
			return true;
		if (white || black)
			return eighteenMoveStalemate();
		return cannotMove(); // if false - stalemate
	}

	private boolean cannotMove()
	{
		for (Piece piece : pieces.get(whitesTurn ? 0 : 1))
			for (int toRow = 0; toRow < 8; toRow++)
				for (int toCol = 0; toCol < 8; toCol++)
					if (piece.isLegalMove(toRow, toCol, pieces, chessBoard, currKing, true))
						return false;
		return true;
	}

	private boolean oneKing(boolean white)
	{
		return pieces.get(white ? 0 : 1).size() == 1;
	}

	private boolean eighteenMoveStalemate()
	{
		countingTurns = true;
		return staleTurns == 18;
	}

	// public move checks
	public boolean isValidPieceThere(int row, int col)
	{
		Piece currPiece = chessBoard[row][col];
		return currPiece != null && currPiece.isWhite() == whitesTurn;
	}

	public boolean canMoveThere(int fromRow, int fromCol, int toRow, int toCol, boolean surpress)
	{
		Piece currPiece = chessBoard[fromRow][fromCol];
		return currPiece.isLegalMove(toRow, toCol, pieces, chessBoard, currKing, surpress);
	}

	// public getters
	public boolean getDebug()
	{
		return debug;
	}

	public boolean getCpuGame()
	{
		return cpuGame;
	}

	public int getAILevel()
	{
		return ai == null ? 0 : ai.getLevel();
	}

	public boolean getNetGame()
	{
		return networkGame;
	}

	public boolean getTurn()
	{
		return playerTurn;
	}

	public boolean getWhite()
	{
		return whitesTurn;
	}

	public String getName(int row, int col)
	{
		return chessBoard[row][col].toString();
	}

	public ArrayList<ArrayList<Piece>> getPieces()
	{
		return pieces;
	}

	// public setters

	public void reverseDebug()
	{
		debug = !debug;
	}

	public void reverseCpu()
	{
		cpuGame = !cpuGame;
	}

	public void setTurn(boolean playerTurn)
	{
		this.playerTurn = playerTurn;
	}

	// Network functionality

	public void netMove()
	{
		System.out.println("\nWaiting for other players move");
		try
		{
			int[] data = network.getMove();
			performMove(data[0], data[1], data[2], data[3]);
		}
		catch (IOException | ClassNotFoundException ex)
		{
			System.out.println(ex);
			forceEnd = true;
		}
	}

	// This method is used to reference saved games
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Piece[][] getBoard()
	{
		return chessBoard;
	}

	public int getStale()
	{
		return staleTurns;
	}

	public int[] getFT()
	{
		return new int[] { fr, fc, tr, tc };
	}
}
