package chess;
import java.io.IOException;

public class Display {
	public static void display(boolean whitesTurn, boolean useJansi, Piece[][] chessBoard, int fr, int fc, int tr,
			int tc) {
		int out = whitesTurn ? 47 : 0;
		int in = whitesTurn ? 7 : 0;
		int minOut = whitesTurn ? -1 : 48;
		int minIn = whitesTurn ? -1 : 8;
		int chg = whitesTurn ? -1 : 1;
		String letters1 = "        A             B             C             D             E             F             G             H        ";
		String letters2 = "        H             G             F             E             D             C             B             A        ";
		String reset = "\u001B[0m";
		String bxWhite = useJansi ? "\u001B[107m" : "\u001B[48;2;57;32;12m";
		String bgWhite = useJansi ? "\u001B[47m" : "\u001B[48;2;249;218;180m";
		String bgBlack = useJansi ? "\u001B[100m" : "\u001B[48;2;127;99;95m";
		String bgGreen = "\u001B[42m";
		String bgCyan = "\u001B[46m";
		String fgWhite = useJansi ? "\u001B[97m" : "\u001B[38;2;205;205;205m";
		String fgBlack = useJansi ? "\u001B[30m" : "\u001B[38;2;40;40;40m";
		String fgBlue = useJansi ? "\u001B[34m" : "\u001B[38;2;112;140;78m";
		String fgNumLets = useJansi ? "\u001B[30m" : "\u001B[38;2;241;222;190m";
		String board = "";
		board += ("\n" + bxWhite + fgNumLets + (whitesTurn ? letters1 : letters2) + " " + reset + "\n");
		for (int i = out; i != minOut; i += chg) {
			boolean numRow = i % 6 + 1 == 3;
			board += (bxWhite + fgNumLets + (numRow ? i / 6 + 1 + " " : "  ") + reset);
			for (int j = in; j != minIn; j += chg) {
				Boolean isWhite = chessBoard[i / 6][j] != null ? chessBoard[i / 6][j].isWhite() : null;
				boolean ijTheSame = i / 6 % 2 == j % 2;
				boolean isNull = chessBoard[i / 6][j] == null;
				boolean from = i / 6 == fr && j == fc;
				boolean to = i / 6 == tr && j == tc;
				board += ((from ? bgCyan : to ? bgGreen : ijTheSame ? bgWhite : bgBlack)
						+ (isNull ? fgBlue : isWhite ? fgWhite : fgBlack) + PieceSection(i, j, whitesTurn, chessBoard,
								(from ? bgCyan : to ? bgGreen : ijTheSame ? bgWhite : bgBlack))
						+ reset);
			}
			board += (bxWhite + fgNumLets + (numRow ? " " + (i / 6 + 1) : "  ") + reset);
			board += "\n";
		}
		board += (bxWhite + fgBlack + " " + (whitesTurn ? letters1 : letters2) + reset);
		System.out.println(board);
	}

	public static String PieceSection(int i, int j, boolean whitesTurn, Piece[][] chessBoard, String bg) {
		boolean numRow = i % 6 == (whitesTurn ? 0 : 5);
		boolean empty = chessBoard[i / 6][j] == null;
		String numLet = (char) (72 - j) + "" + (i / 6 + 1);
		int iconRow = (whitesTurn ? 47 - i : i) % 6;
		return (empty ? "            " : chessBoard[i / 6][j].getIcon(iconRow))
				+ (numRow ? ("\u001B[0m" + bg + "\u001B[30m" + numLet) : "  ");
	}
	//do we need the 4 ints?
	public static void debug(Piece[][] chessBoard, boolean whitesTurn, boolean useJansi, int fr, int fc, int tr,
			int tc) {
		String lets = whitesTurn || useJansi ? "  | A  | B  | C  | D  | E  | F  | G  | H  |"
				: "  | H  | G  | F  | E  | D  | C  | B  | A  |";
		String dash = "--|----|----|----|----|----|----|----|----|";
		int out = whitesTurn || useJansi ? 7 : 0;
		int in = out;
		int minOut = whitesTurn || useJansi ? -1 : 8;
		int minIn = minOut;
		int chg = whitesTurn || useJansi ? -1 : 1;

		System.out.println(lets);
		for (int i = out; i != minOut; i += chg) {
			System.out.println(dash);
			for (int j = in; j != minIn; j += chg) {
				System.out.print((j == out ? (i + 1) : "") + " | "
						+ (chessBoard[i][j] == null ? "  "
								: ((chessBoard[i][j].isWhite() ? "w" : "b") + chessBoard[i][j].toString().charAt(6)))
						+ (j == minIn - chg ? " |" : ""));
			}
			System.out.println();
		}
		System.out.println(dash);
	}

	public static void clear() throws InterruptedException, IOException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}
}
