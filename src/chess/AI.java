package chess;

import java.util.ArrayList;

public class AI
{
	private ChessBoard CB;
	private int level;

	public AI(int level, ChessBoard CB)
	{
		this.CB = CB;
		this.level = level;
	}

	public void doAIMove()
	{
		switch (level)
		{
			case 1:
				dumbAI();
				break;
			case 2:
				easyAI();
				break;
			default:
				smartAI();
		}
	}

	public int getLevel()
	{
		return level;
	}

	private void dumbAI()
	{
		boolean legalMoveInput;
		int fromRow, fromCol, toRow, toCol;
		do
		{
			do
			{
				fromCol = (int) (Math.random() * ((7 - 0) + 1));
				fromRow = (int) (Math.random() * ((7 - 0) + 1));
				legalMoveInput = CB.isValidPieceThere(fromRow, fromCol);
			} while (!legalMoveInput);

			do
			{
				toCol = (int) (Math.random() * ((7 - 0) + 1));
				toRow = (int) (Math.random() * ((7 - 0) + 1));
			} while ((toCol == fromCol && toRow == fromRow));
		} while (!CB.canMoveThere(fromRow, fromCol, toRow, toCol, true));
		CB.performMove(fromRow, fromCol, toRow, toCol);
	}

	private void easyAI()
	{
		Piece[][] board = CB.getBoard();
		int tempValue = 0;
		int tempMoveToRow = 0;
		int tempMoveFromCol = 0;
		int tempMoveToCol = 0;
		int tempMoveFromRow = 0;
		int blackOrWhite = (CB.getWhite() && !CB.getTurn() ? 0 : 1);

		for (Piece p : CB.getPieces().get(blackOrWhite))
			for (int m : potentialMoves(p))
				if (CB.canMoveThere(p.getRow(), p.getCol(), m % 10, m / 10, true) && board[m % 10][m / 10] != null
						&& tempValue < board[m % 10][m / 10].getAIValue())
				{
					tempValue = board[m % 10][m / 10].getAIValue();
					tempMoveToRow = m % 10;
					tempMoveToCol = m / 10;
					tempMoveFromRow = p.getRow();
					tempMoveFromCol = p.getCol();
				}

		if (tempValue > 0)
			CB.performMove(tempMoveFromRow, tempMoveFromCol, tempMoveToRow, tempMoveToCol);
		else
			dumbAI();
	}

	private void smartAI()
	{
		Piece[][] board = CB.getBoard();
		int tempValue = 0;
		Piece[][] depthBoard = boardCopy(board);

		int tempMoveToRow = 0;
		int tempMoveFromCol = 0;
		int tempMoveToCol = 0;
		int tempMoveFromRow = 0;

		int blackOrWhite = (CB.getWhite() && !CB.getTurn() ? 0 : 1);
		for (Piece p : CB.getPieces().get(blackOrWhite))
		{
			for (int m : potentialMoves(p))
			{
				if (CB.canMoveThere(p.getRow(), p.getCol(), m % 10, m / 10, true))
				{
					int valueHolder = (board[m % 10][m / 10] != null ? board[m % 10][m / 10].getAIValue() : 0);
					depthBoard[m % 10][m / 10] = depthBoard[p.getRow()][p.getCol()];
					depthBoard[p.getRow()][p.getCol()] = null;

					if (tempValue < (valueHolder - recursiveMoveCheck(depthBoard, !CB.getWhite(), level)))
					{

						tempValue = valueHolder - recursiveMoveCheck(depthBoard, !CB.getWhite(), level);
						tempMoveToRow = m % 10;
						tempMoveToCol = m / 10;
						tempMoveFromRow = p.getRow();
						tempMoveFromCol = p.getCol();
						depthBoard = boardCopy(board);
					}
					else
					{
						depthBoard[m % 10][m / 10] = depthBoard[p.getRow()][p.getCol()];
						depthBoard[p.getRow()][p.getCol()] = null;

						if (tempValue < (-recursiveMoveCheck(depthBoard, !CB.getWhite(), level)))
						{
							tempValue = (-recursiveMoveCheck(depthBoard, !CB.getWhite(), level));
							tempMoveToRow = m % 10;
							tempMoveToCol = m / 10;
							tempMoveFromRow = p.getRow();
							tempMoveFromCol = p.getCol();
						}
						depthBoard = boardCopy(board);
					}
				}
			}
		}

		if (tempValue > 0)
			CB.performMove(tempMoveFromRow, tempMoveFromCol, tempMoveToRow, tempMoveToCol);
		else
			dumbAI();
	}

	private int recursiveMoveCheck(Piece[][] pieces, boolean white, int limit)
	{
		if (limit == 0)
			return advancedCountPieces(pieces, white);

		Piece[][] board = boardCopy(pieces);
		Piece[][] tempBoard = boardCopy(board);
		int tempValue = 0;

		int tempMoveToRow = 0;
		int tempMoveFromCol = 0;
		int tempMoveToCol = 0;
		int tempMoveFromRow = 0;

		int blackOrWhite = (white ? 0 : 1);
		for (Piece p : setUpArray(board).get(blackOrWhite))
			for (int m : potentialMoves(p))
			{
				if (p.getRow() < 8 && p.getRow() >= 0 && p.getCol() >= 0 && p.getCol() < 8 && m / 10 >= 0 && m % 10 >= 0
						&& m / 10 < 8 && m % 10 < 8)
				{
					int valueHolder = (board[m % 10][m / 10] != null ? board[m % 10][m / 10].getAIValue() : 0);
					if (tempValue < valueHolder - recursiveMoveCheck(tempBoard, !white, limit - 1))
						tempValue = valueHolder + (-recursiveMoveCheck(tempBoard, !white, limit - 1));
				}
				if (p.getRow() < 8 && p.getRow() >= 0 && p.getCol() >= 0 && p.getCol() < 8 && m / 10 >= 0
						&& m % 10 >= 10 && m / 10 < 8 && m % 10 < 8)
				{
					tempBoard[m % 10][m / 10] = tempBoard[p.getRow()][p.getCol()];
					tempBoard[p.getRow()][p.getCol()] = null;

					int place = -recursiveMoveCheck(tempBoard, !white, limit - 1);
					if (tempValue < place)
						tempValue = place;
				}
				tempBoard = boardCopy(board);
			}

		if (tempValue > 0)
		{
			board[tempMoveToRow][tempMoveToCol] = board[tempMoveFromRow][tempMoveFromCol];
			board[tempMoveFromRow][tempMoveFromCol] = null;
		}

		return tempValue - recursiveMoveCheck(board, !white, limit - 1);
	}

	private ArrayList<ArrayList<Piece>> setUpArray(Piece[][] chessBoard)
	{
		ArrayList<ArrayList<Piece>> pieces;
		pieces = new ArrayList<>();
		pieces.add(new ArrayList<Piece>());
		pieces.add(new ArrayList<Piece>());
		for (Piece[] pa : chessBoard)
			for (Piece currPiece : pa)
				if (currPiece != null)
					pieces.get(currPiece.isWhite() ? 0 : 1).add(currPiece);
		return pieces;
	}

	private int advancedCountPieces(Piece[][] pieces, boolean white)
	{
		int value = 0;
		for (int i = 0; i < 8; i++)
			for (int i2 = 0; i2 < 8; i2++)

				if (pieces[i][i2] != null)
					if (pieces[i][i2].isWhite())
						value += pieces[i][i2].getAIValue();
					else
						value -= pieces[i][i2].getAIValue();
		if (!white)
			value *= -1;

		return value;

	}

	private Piece[][] boardCopy(Piece[][] pieces)
	{
		Piece[][] newPieces = new Piece[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (pieces[i][j] != null)
					newPieces[i][j] = pieces[i][j];

		return newPieces;
	}

	private ArrayList<Integer> potentialMoves(Piece piece)
	{
		ArrayList<Integer> moves = new ArrayList<Integer>();

		if (piece instanceof Pawn)
		{
			if (piece.getRow() < 7)
			{
				moves.add(piece.getRow() + 1 + (piece.getCol()) * 10);
				moves.add(piece.getRow() - 1 + (piece.getCol()) * 10);
			}

			if (piece.getRow() < 7 && piece.getCol() < 7)
			{
				moves.add(piece.getRow() + 1 + (piece.getCol() + 1) * 10);
				moves.add(piece.getRow() - 1 + (piece.getCol() + 1) * 10);
			}

			if (piece.getRow() < 7 && piece.getCol() > 0)
			{
				moves.add(piece.getRow() + 1 + (piece.getCol() - 1) * 10);
				moves.add(piece.getRow() - 1 + (piece.getCol() - 1) * 10);
			}

			if (piece.getRow() == 1)
			{
				moves.add(piece.getRow() + 2 + (piece.getCol()) * 10);
				moves.add(piece.getRow() - 2 + (piece.getCol()) * 10);
			}

		}

		if (piece instanceof Horse)
		{

			if (piece.getRow() < 6 && piece.getCol() < 7)
				moves.add(piece.getRow() + 2 + (piece.getCol() + 1) * 10);

			if (piece.getRow() < 7 && piece.getCol() < 6)
				moves.add(piece.getRow() + 1 + (piece.getCol() + 2) * 10);

			if (piece.getRow() < 6 && piece.getCol() > 0)
				moves.add(piece.getRow() + 2 + (piece.getCol() - 1) * 10);

			if (piece.getRow() < 7 && piece.getCol() > 1)
				moves.add(piece.getRow() + 1 + (piece.getCol() - 2) * 10);

			if (piece.getRow() > 0 && piece.getCol() > 1)
				moves.add(piece.getRow() - 1 + (piece.getCol() - 2) * 10);

			if (piece.getRow() > 1 && piece.getCol() > 0)
				moves.add(piece.getRow() - 2 + (piece.getCol() - 1) * 10);

			if (piece.getRow() > 0 && piece.getCol() < 7)
				moves.add(piece.getRow() - 2 + (piece.getCol() + 1) * 10);

			if (piece.getRow() > 1 && piece.getCol() < 6)
				moves.add(piece.getRow() - 1 + (piece.getCol() + 2) * 10);

		}

		if (piece instanceof Rook)
		{
			for (int i = piece.getRow(); i < 8; i++)
			{
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}
			for (int i = piece.getRow(); i >= 0; i--)
			{
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}

			for (int i = piece.getCol(); i < 8; i++)
			{
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}

			for (int i = piece.getCol(); i >= 0; i--)
			{
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}

		}

		if (piece instanceof Bishop)
		{
			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() + i > 7 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() + i > 7 || piece.getCol() - i < 0)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() - i))
					break;
			}

			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() - i < 0 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() - i < 0 || piece.getCol() - i < 0)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() - i) * 10);

				if (piece.getRow() - i >= 0 && piece.getCol() - i >= 0)
					if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() - i))
						break;
			}
		}

		if (piece instanceof Queen)
		{
			for (int i = piece.getRow(); i < 8; i++)
			{
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}

			for (int i = piece.getRow(); i >= 0; i--)
			{
				moves.add(i + piece.getCol() * 10);
				if (CB.isValidPieceThere(i, piece.getCol()))
					break;
			}

			for (int i = piece.getCol(); i < 8; i++)
			{
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}

			for (int i = piece.getCol(); i >= 0; i--)
			{
				moves.add(piece.getRow() + i * 10);
				if (CB.isValidPieceThere(piece.getRow(), i))
					break;
			}

			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() + i > 7 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() + i > 7 || piece.getCol() - i < 0)
					break;

				moves.add(piece.getRow() + i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() + i, piece.getCol() - i))
					break;
			}

			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() - i < 0 || piece.getCol() + i > 7)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() + i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() + i))
					break;
			}

			for (int i = 0; i < 8; i++)
			{
				if (piece.getRow() - i < 0 || piece.getCol() - i < 0)
					break;

				moves.add(piece.getRow() - i + (piece.getCol() - i) * 10);
				if (CB.isValidPieceThere(piece.getRow() - i, piece.getCol() - i))
					break;
			}
		}

		if (piece instanceof King)
		{

			if (piece.getRow() < 7)
			{
				moves.add(piece.getRow() + 1 + piece.getCol() * 10);

				if (piece.getCol() < 7)
					moves.add(piece.getRow() + 1 + (piece.getCol() + 1 * 10));

				if (piece.getCol() > 0)
					moves.add(piece.getRow() + 1 + (piece.getCol() - 1 * 10));

			}

			if (piece.getRow() > 0)
			{
				moves.add(piece.getRow() - 1 + piece.getCol() * 10);

				if (piece.getCol() < 7)
					moves.add(piece.getRow() - 1 + (piece.getCol() + 1 * 10));

				if (piece.getCol() > 0)
					moves.add(piece.getRow() - 1 + (piece.getCol() - 1 * 10));

			}

			if (piece.getCol() < 7)
				moves.add(piece.getRow() + (piece.getCol() + 1 * 10));

			if (piece.getCol() > 0)
				moves.add(piece.getRow() + (piece.getCol() - 1 * 10));
		}

		return moves;
	}
}
