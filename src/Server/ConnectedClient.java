package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ConnectedClient extends Thread
{
	private String username;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean available;
	private volatile HashMap<String, ConnectedClient> players;
	private volatile HashMap<String, Game> games;

	public ConnectedClient(Socket socket, HashMap<String, ConnectedClient> players, HashMap<String, Game> games)
			throws IOException
	{
		this.players = players;
		this.socket = socket;
		this.games = games;
		available = false;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run()
	{
		try
		{
			createUserName();
			startMenu();
		}
		catch (IOException e)
		{
			// TODO
			// Inform other players?
			// Remove self from lists
		}
	}

	private void createUserName() throws IOException
	{
		String username;
		boolean good = false;

		do
		{
			do
			{
				username = in.readUTF();
				if (players.containsKey(username))
				{
					out.writeBoolean(good);
					out.flush();
				}
			} while (players.containsKey(username));
			synchronized (players)
			{
				if (players.containsKey(username))
				{
					out.writeBoolean(good);
					out.flush();
				}
				else
				{
					players.put(username, this);
					good = true;
				}
			}
		} while (!good);
		this.username = username;
		out.writeBoolean(good);
		out.flush();
	}

	private void startMenu() throws IOException
	{
		int choice = in.readInt();
		switch (choice)
		{
			case 1:
				available = true;
				break;
			case 2:
				ArrayList<String> temp = new ArrayList<>();
				temp.addAll(players.values().stream().filter(s -> s.available).map(s -> s.getUserName())
						.collect(Collectors.toList()));
				out.writeObject(temp);
				out.flush();
				String opponent = in.readUTF();
				Game game = new Game(players.get(opponent), this);
				game.start();
				games.put(game.toString(), game);
				break;
			case 3:

				break;

			default:
				break;
		}

	}

	public String getUserName()
	{
		return username;
	}

	public int[] getMove() throws IOException
	{
		try
		{
			return (int[]) in.readObject();
		}
		catch (ClassNotFoundException e)
		{
			return null;
		}
	}

	public void sendMove(int[] move) throws IOException
	{
		out.writeObject(move);
		out.flush();
	}

	@Override
	public String toString()
	{
		return username + " connected at " + socket.getInetAddress();
	}

	public void informClient(String opponent) throws IOException
	{
		available = false;
		out.writeUTF(opponent);
		out.flush();
	}

	public void sendAllMoves(ArrayList<int[]> moves)
	{
		// TODO Auto-generated method stub

	}
}
