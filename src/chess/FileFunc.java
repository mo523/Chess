package chess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileFunc
{
	private static final Path SAVE = Paths.get("savedgames");
	private static final String LS = System.lineSeparator();
	private static final String S = " ";

	public static void saveGame(ChessBoard CB, String name) throws IOException
	{
		if (!Files.exists(SAVE) || Files.isDirectory(SAVE))
		{
			Files.createFile(SAVE);
			Files.writeString(SAVE, "#CHESS SAVED GAMES. DO NOT MANUALLY EDIT THIS FILE!!!" + LS);
		}

		StringBuilder data = new StringBuilder();
		data.append("$" + name + LS);
		data.append(CB.getDebug() + S + CB.getWhite() + S + CB.getAILevel() + S + CB.getTurn() + S + CB.getStale() + LS);
		int[] FT = CB.getFT();
		data.append(FT[0] + S + FT[1] + S + FT[2] + S + FT[3] + LS);
		data.append((CB.getPieces().get(0).size() + CB.getPieces().get(1).size()) + LS);
		CB.getPieces().forEach(ia -> ia
				.forEach(p -> data.append(p.isWhite() + S + p.toString() + S + p.getRow() + S + p.getCol() + LS)));
		Files.write(SAVE, data.toString().getBytes(), StandardOpenOption.APPEND);
	}

	public static ArrayList<String> getNames() throws IOException
	{
		ArrayList<String> names = new ArrayList<>();
		if (Files.exists(SAVE) && !Files.isDirectory(SAVE))
			for (String line : Files.readAllLines(SAVE))
				if (line.startsWith("$"))
					names.add(line.substring(1));
		return names;
	}

	public static ChessBoard loadGame(String name) throws IOException
	{
		name = "$" + name;
		ArrayList<String> games = (ArrayList<String>) Files.readAllLines(SAVE);
		int gameloc = -1;
		for (int i = 1; i < games.size(); i++)
			if (games.get(i).equals(name))
			{
				gameloc = i + 1;
				break;
			}
		if (gameloc == -1)
			throw new IOException("Game not found");
		String la[] = games.get(gameloc++).split(S);
		boolean debug = la[0].equals("true");
		boolean white = la[1].equals("true");
		int ai = Integer.parseInt(la[2]);
		boolean turn = la[3].equals("true");
		int stale = Integer.parseInt(la[4]);

		int[] ft = new int[4];
		la = games.get(gameloc++).split(S);
		ft[0] = Integer.parseInt(la[0]);
		ft[1] = Integer.parseInt(la[1]);
		ft[2] = Integer.parseInt(la[2]);
		ft[3] = Integer.parseInt(la[3]);
		int[][] pieces = new int[Integer.parseInt(games.get(gameloc++))][4];
		while (gameloc < games.size() && !(la = games.get(gameloc++).split(S))[0].startsWith("$"))
		{
			pieces[gameloc - 6][0] = la[0].equals("true") ? 1 : 0;

			switch (la[1].charAt(0))
			{
				case 'P':
					pieces[gameloc - 6][1] = 0;
					break;
				case 'R':
					pieces[gameloc - 6][1] = 1;
					break;
				case 'B':
					pieces[gameloc - 6][1] = 2;
					break;
				case 'H':
					pieces[gameloc - 6][1] = 3;
					break;
				case 'Q':
					pieces[gameloc - 6][1] = 4;
					break;
				case 'K':
					pieces[gameloc - 6][1] = 5;
					break;
				default:
					pieces[gameloc - 6][1] = -1;
					break;
			}
			pieces[gameloc - 6][2] = Integer.parseInt(la[2]);
			pieces[gameloc - 6][3] = Integer.parseInt(la[3]);
		}

		return new ChessBoard(debug, white, ai, turn, stale, ft, pieces);
	}
}
