import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SaveGameFunctionality {
	private static Scanner kyb = new Scanner(System.in);

	public static void saveGame(Piece[][] chessBoard, boolean debug,
			Piece whiteKing, Piece blackKing, boolean whitesTurn,
			boolean cpuGame, boolean cpuTurn, boolean startCountingTurns,
			int turns) throws FileNotFoundException, IOException,
			ClassNotFoundException {

		SavedGame[] savedGames = readFile();
		ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream("Saved Games"));
		if (savedGames != null)
			for (SavedGame s : savedGames)
				saver.writeObject(s);
		System.out.println("What name do you want to save the game as?");
		saver.writeObject(new SavedGame(chessBoard, debug, whiteKing,
				blackKing, whitesTurn, cpuGame, cpuTurn, startCountingTurns,
				turns, kyb.next()));
		saver.close();
	}

	private static SavedGame[] readFile() throws IOException,
			ClassNotFoundException {
		ObjectInputStream loader = null;
		boolean found = true;
		try {
			loader = new ObjectInputStream(new FileInputStream("Saved Games"));
		} catch (FileNotFoundException e) {
			found = false;
		}

		if (found) {
			Collection<SavedGame> list = new LinkedList<SavedGame>();
			return (SavedGame[]) loopThruFile(list, loader).toArray(
					new SavedGame[list.size()]);
		}
		return null;
	}

	private static Collection<SavedGame> loopThruFile(
			Collection<SavedGame> collection, ObjectInputStream loader)
			throws ClassNotFoundException, IOException {
		while (true) {
			try {
				collection.add((SavedGame) loader.readObject());
			} catch (EOFException e) {
				break;
			}
		}
		return collection;
	}

	public static SavedGame loadSavedGame() throws FileNotFoundException,
			IOException, ClassNotFoundException {
		List<SavedGame> list = new ArrayList<SavedGame>();
		loopThruFile(list, new ObjectInputStream(new FileInputStream("Saved Games")));
		System.out.println("Which game?");
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ") " + list.get(i).getName());
		}

		return list.get(kyb.nextInt() - 1);
	}
}
