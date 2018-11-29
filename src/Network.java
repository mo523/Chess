import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {
	private Socket socket;
	private boolean server;
	private ServerSocket listener;

	public Network() throws IOException {
		server = true;
		InetAddress[] lh = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
		if (lh.length == 1)
			System.out.println("IP Address: " + lh[0].getHostAddress());
		else {
			System.out.println("Multiple local IP Addresses found:");
			for (int i = 0; i < lh.length; i++)
				if (lh[i] instanceof Inet4Address)
					System.out.println((i + 1) + ": " + lh[i].getHostAddress());
		}
		listener = new ServerSocket(9090);
		socket = listener.accept();
	}

	public Network(String adr) throws UnknownHostException, IOException {
		server = false;
		socket = new Socket(adr, 9090);
	}

	public void sendServerData(int fr, int fc, int tr, int tc) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(fr + " " + fc + " " + tr + " " + tc);
		socket.shutdownOutput();
	}

	public int[] getClientData() throws IOException {
		InputStreamReader isr = new InputStreamReader(socket.getInputStream());
		BufferedReader input = new BufferedReader(isr);
		String sin[] = input.readLine().split(" ");
		int[] ins = new int[4];
		for (int i = 0; i < 4; i++)
			ins[i] = Integer.parseInt(sin[i]);
		return ins;
	}

	public void close() throws IOException {
		socket.close();
	}

	public boolean isServer() {
		return server;
	}

}
