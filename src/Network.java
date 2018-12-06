import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {
	private Socket socket;
	private boolean host;
	PrintWriter out;
	BufferedReader in; 
	
	public Network(String game) throws UnknownHostException, IOException {
		host = game.length() > 0;
		socket = new Socket("moshehirsch.com", 9090);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
	}

	public void sendServerData(int fr, int fc, int tr, int tc) throws IOException {
		out.println(fr + " " + fc + " " + tr + " " + tc);
	}

	public int[] getClientData() throws IOException {
		String sin[] = in.readLine().split(" ");
		int[] ins = new int[4];
		for (int i = 0; i < 4; i++)
			ins[i] = Integer.parseInt(sin[i]);
		return ins;
	}

	public String getGames() throws IOException
	{
		return in.readLine();
	}
	
	public void close() throws IOException {
		socket.close();
	}

	public void join(int game)
	{
		out.write(game);
	}
	
	public boolean isHost() {
		return host;
	}

}
