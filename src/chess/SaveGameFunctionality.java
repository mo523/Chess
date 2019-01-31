package chess;
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

	/**
	 * 
	 * @param chessBoard
	 * @param debug
	 * @param whiteKing
	 * @param blackKing
	 * @param whitesTurn
	 * @param cpuGame
	 * @param cpuTurn
	 * @param startCountingTurns
	 * @param turns
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void saveGame(ChessBoard CB) throws FileNotFoundException, IOException, ClassNotFoundException {
		ChessBoard[] savedGames = readFile("Saved Games");
		ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream("Saved Games"));
		if (savedGames != null)
			for (ChessBoard s : savedGames)
				saver.writeObject(s);
		System.out.println("What name do you want to save the game as?");
		CB.setName(kyb.next());
		saver.writeObject(CB);
		saver.close();
	}

	@SuppressWarnings("resource")
	private static ChessBoard[] readFile(String fileName) throws IOException, ClassNotFoundException {
		ObjectInputStream loader = null;
		boolean found = true;
		try {
			loader = new ObjectInputStream(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			found = false;
		}

		if (found) {
			Collection<ChessBoard> collection = loopThruFile(loader);//i define the collection here and not in loopThruFile so i can use collection.size()
			return (ChessBoard[]) collection.toArray(new ChessBoard[collection.size()]);
		}
		return null;
	}

	private static Collection<ChessBoard> loopThruFile(ObjectInputStream loader) throws ClassNotFoundException, IOException {
		Collection<ChessBoard> collection = new LinkedList<ChessBoard>();
		while (true) {
			try {
				collection.add((ChessBoard) loader.readObject());
			} catch (EOFException e) {
				break;
			}
		}
		loader.close();
		return collection;
	}

	@SuppressWarnings("resource")
	public static ChessBoard loadSavedGame() throws FileNotFoundException, IOException, ClassNotFoundException {
		List<ChessBoard> list = new ArrayList<ChessBoard>( loopThruFile( new ObjectInputStream(new FileInputStream("Saved Games")) ) );
		System.out.println("Which game?");
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ") " + list.get(i).getName());
		}
		return list.get(kyb.nextInt() - 1);
	}
}
