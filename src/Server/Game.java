package Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Game
{
	private volatile ConnectedClient p1;
	private volatile ConnectedClient p2;
	private ArrayList<int[]> moves;
	private HashSet<ConnectedClient> observers;

	public Game(ConnectedClient p1, ConnectedClient p2)
	{
		this.p1 = p1;
		this.p2 = p2;
		moves = new ArrayList<>();
		observers = new HashSet<>();
	}

	public void playGame() throws IOException
	{
		boolean p1sTurn = new Random().nextBoolean();

		do
		{
			int[] move;
			if (p1sTurn)
			{
				move = p1.getMove();
				p2.sendMove(move);
			}
			else
			{
				move = p2.getMove();
				p1.sendMove(move);
			}
			new Thread(() -> sendMovesToObservers(move)).start();
			p1sTurn = !p1sTurn;
		} while (true);
	}

	private void sendMovesToObservers(int[] move)
	{
		for (ConnectedClient co : observers)
			try
			{
				co.sendMove(move);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	@Override
	public String toString()
	{
		return p1.getUserName() + " vs. " + p2.getUserName();
	}
}
