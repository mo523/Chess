package Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Game extends Thread
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

	@Override
	public void run()
	{
		try
		{
			p1.informClient(p2.getName());
			playGame();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playGame() throws IOException
	{
		boolean p1sTurn = true;
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
			moves.add(move);
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
	
	public void addObserver(ConnectedClient c)
	{
		c.sendAllMoves(moves);
		observers.add(c);
	}
	
	@Override
	public String toString()
	{
		return p1.getUserName() + " vs. " + p2.getUserName();
	}
}
