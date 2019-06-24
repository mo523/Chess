package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server
{
	private static volatile HashMap<String, ConnectedClient> players;
	private static volatile HashMap<String, Game> games;

	public static void main(String[] args)
	{
		players = new HashMap<>();
		games = new HashMap<>();
		listenForConnections();
	}

	private static void listenForConnections()
	{
		while (true)
		{
			try
			{
				ServerSocket ss = new ServerSocket(4368);
				Socket socket = ss.accept();
				ss.close();
				new ConnectedClient(socket, players, games).start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
