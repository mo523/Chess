package chess;

public class Display
{
	public static void display(boolean whitesTurn, Piece[][] chessBoard, int fr, int fc, int tr, int tc)
	{
		StringBuilder sb = new StringBuilder();

		boolean ebc = false;
		int out = whitesTurn ? 47 : 0;
		int in = whitesTurn ? 0 : 7;
		int minOut = whitesTurn ? -1 : 48;
		int minIn = whitesTurn ? 8 : -1;
		int chg = whitesTurn ? -1 : 1;
		String letters1 = "        A             B             C             D             E             F             G             H        ";
		String letters2 = "        H             G             F             E             D             C             B             A        ";
		String reset = "\u001B[0m";
		String bxWhite = ebc ? "\u001B[107m" : "\u001B[48;2;57;32;12m";
		String bgBlack = ebc ? "\u001B[47m" : "\u001B[48;2;249;218;180m";
		String bgWhite = ebc ? "\u001B[100m" : "\u001B[48;2;127;99;95m";
		String bgGreen = ebc ? "\u001B[42m" : "\u001B[48;2;41;46;75m";
		String bgCyan = ebc ? "\u001B[46m" : "\u001B[48;2;41;46;85m";
		String fgWhite = ebc ? "\u001B[97m" : "\u001B[38;2;205;205;205m";
		String fgBlack = ebc ? "\u001B[30m" : "\u001B[38;2;40;40;40m";
		String fgBlue = ebc ? "\u001B[34m" : "\u001B[38;2;112;140;78m";
		String fgNumLets = ebc ? "\u001B[30m" : "\u001B[38;2;241;222;190m";

		sb.append("\r\n" + bxWhite + fgNumLets + (whitesTurn ? letters1 : letters2) + " " + reset + "\r\n");
		for (int i = out; i != minOut; i += chg)
		{
			boolean numRow = i % 6 + 1 == 3;
			sb.append(bxWhite + fgNumLets + (numRow ? i / 6 + 1 + " " : "  ") + reset);
			for (int j = in; j != minIn; j -= chg)
			{
				Boolean isWhite = chessBoard[i / 6][j] != null ? chessBoard[i / 6][j].isWhite() : null;
				boolean ijTheSame = i / 6 % 2 == j % 2;
				boolean isNull = chessBoard[i / 6][j] == null;
				boolean from = i / 6 == fr && j == fc;
				boolean to = i / 6 == tr && j == tc;
				sb.append(
						(from ? bgCyan : to ? bgGreen : ijTheSame ? bgWhite : bgBlack)
								+ (isNull ? fgBlue : isWhite ? fgWhite : fgBlack) + PieceSection(i, j, whitesTurn,
										chessBoard, (from ? bgCyan : to ? bgGreen : ijTheSame ? bgWhite : bgBlack))
								+ reset);
			}
			sb.append(bxWhite + fgNumLets + " " + (numRow ? i / 6 + 1 : " ") + reset + "\r\n");
		}
		sb.append(bxWhite + fgNumLets + (whitesTurn ? letters1 : letters2) + " " + reset + "\r\n");
		System.out.println(sb.toString());
	}

	private static String PieceSection(int i, int j, boolean whitesTurn, Piece[][] chessBoard, String bg)
	{
		boolean numRow = i % 6 == (whitesTurn ? 0 : 5);
		boolean empty = chessBoard[i / 6][j] == null;
		String numLet = (char) (65 + j) + "" + (i / 6 + 1);
		int iconRow = (whitesTurn ? 47 - i : i) % 6;
		return (empty ? "            " : chessBoard[i / 6][j].getIcon(iconRow))
				+ (numRow ? ("\u001B[0m" + bg + "\u001B[30m" + numLet) : "  ");
	}

	public static void debug(Piece[][] chessBoard)
	{
		String lets = "  | A  | B  | C  | D  | E  | F  | G  | H  |";
		String dash = "\r\n--|----|----|----|----|----|----|----|----|";
		System.out.print(lets);
		System.out.println(dash);
		for (int i = 7; i >= 0; i--)
		{
			for (int j = 0; j < 8; j++)
			{
				System.out.print((j == 0 ? (i + 1) : "") + " | "
						+ (chessBoard[i][j] == null ? "  "
								: ((chessBoard[i][j].isWhite() ? "w" : "b") + chessBoard[i][j].toString().charAt(6)))
						+ (j == 7 ? " |" : ""));
			}
			System.out.println(dash);
		}
		System.out.println("\r\n");
	}
}
