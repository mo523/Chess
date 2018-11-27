import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {
	private Socket socket;
	private boolean server;
	private ServerSocket listener;

	public Network() throws IOException
	{
		server = true;
		InetAddress localhost = InetAddress.getLocalHost(); 
		System.out.println("Local IP: " + localhost.getHostAddress());
		
		listener= new ServerSocket(9090);
		socket = listener.accept();
	}
	public Network(String adr) throws UnknownHostException, IOException
	{
		server = false;
		socket = new Socket(adr, 9090);
	}
	
	public void close() throws IOException
	{
		socket.close();
	}

}
