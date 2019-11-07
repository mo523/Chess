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

	public static void saveGame(ChessBoard CB, String name) throws IOException
	{
		if (!Files.exists(SAVE) || Files.isDirectory(SAVE))
		{
			Files.createFile(SAVE);
			Files.writeString(SAVE, "#CHESS SAVED GAMES. DO NOT MANUALLY EDIT THIS FILE!!!" + LS);
		}

		StringBuilder data = new StringBuilder();
		data.append("$" + name + LS);
		data.append(CB.getWhite() + LS);
		data.append(CB.getCpuGame() + LS);
		data.append(CB.getTurn() + LS);
		data.append(CB.getStale() + LS);
		int[] FT = CB.getFT();
		data.append(FT[0] + " " + FT[1] + " " + FT[2] + " " + FT[3] + LS);
		CB.getPieces().forEach(ia -> ia.forEach(
				p -> data.append(p.isWhite() + " " + p.toString() + " " + p.getRow() + " " + p.getCol() + LS)));
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
		int[] ft = new int[4];
		String[] la = games.get(gameloc + 5).split(" ");
		ft[0] = Integer.parseInt(la[0]);
		ft[1] = Integer.parseInt(la[1]);
		ft[2] = Integer.parseInt(la[2]);
		ft[3] = Integer.parseInt(la[3]);

		la = games.get(gameloc + 5).split(" ");
		int[][][] pieces = new int[Integer.parseInt(la[0])][Integer.parseInt(la[1])][3];
		for (int i = 0; i < 2; i++)
			for (int j = gameloc + 5 + i * Integer.parseInt(la[0]); j < 0; j++)
			{
				la = games.get(j).split(" ");
				pieces[i][j/* no */][0] = Integer.parseInt(la[0]);
				pieces[i][j/* no */][1] = Integer.parseInt(la[1]);
				pieces[i][j/* no */][2] = Integer.parseInt(la[2]);
			}

		return new ChessBoard(true, 0, true, 0, ft, pieces);
	}
}
