package chess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Network
{
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Network(Scanner kb) throws IOException
	{
		System.out.println("Connecting to chess server...");
		try
		{
			socket = new Socket("https://www.moshehirsch.com", 4368);
		}
		catch (IOException e)
		{
			try
			{
				socket = new Socket("192.168.1.100", 4368);
			}
			catch (IOException e1)
			{
				socket = new Socket("127.0.0.1", 4368);
			}
		}
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		System.out.println("Connected");
		logIn(kb);
	}

	private void logIn(Scanner kb) throws IOException
	{
		kb.nextLine();
		System.out.println("Please choose a username");
		String username;
		boolean good;
		do
		{
			username = kb.nextLine();
			out.writeUTF(username);
			out.flush();
			good = in.readBoolean();
			if (!good)
				System.out.println("Username taken, please try another");
		} while (!good);
	}

	public void sendMove(int fr, int fc, int tr, int tc) throws IOException
	{
		out.writeObject(new int[] { fr, fc, tr, tc });
		out.flush();
	}

	public int[] getMove() throws ClassNotFoundException, IOException
	{
		return (int[]) in.readObject();
	}

	public void hostGame() throws IOException
	{
		out.writeInt(1);
		out.flush();
		System.out.println("Waiting for opponent...");
		System.out.println("Connected to: " + in.readUTF());
	}

	public void joinGame(String oppponent) throws IOException
	{
		out.writeUTF(oppponent);
		out.flush();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getPlayers() throws IOException, ClassNotFoundException
	{
		out.writeInt(2);
		out.flush();
		return (ArrayList<String>) in.readObject();
	}
}
