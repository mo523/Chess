package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ConnectedClient extends Thread
{
	private String userName;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private volatile HashMap<String, ConnectedClient> players;
	private volatile HashMap<String, Game> games;

	public ConnectedClient(Socket socket, HashMap<String, ConnectedClient> players, HashMap<String, Game> games)
			throws IOException
	{
		this.players = players;
		this.socket = socket;
		this.games = games;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run()
	{
		createUserName();
		try
		{
			startMenu();
		}
		catch (IOException e)
		{
			// TODO
			// Inform other players?
			// Remove self from lists
		}
	}

	private void createUserName()
	{
		String userName;
		boolean good = false;
		try
		{
			do
			{
				do
				{
					userName = in.readUTF();
					if (players.containsKey(userName))
					{
						out.writeBoolean(good);
						out.flush();
					}
				} while (players.containsKey(userName));
				synchronized (players)
				{
					if (players.containsKey(userName))
					{
						out.writeBoolean(good);
						out.flush();
					}
					else
					{
						players.put(userName, this);
						good = true;
					}
				}
			} while (!good);
			out.writeBoolean(good);
			out.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void startMenu() throws IOException
	{
		while (true)
		{
			int choice = in.readInt();

		}

	}

	public String getUserName()
	{
		return userName;
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
		return userName + " connected at " + socket.getInetAddress();
	}

}
