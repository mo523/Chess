import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
	static ServerSocket listener;
	static Socket socket;
	public static void startServer() throws IOException
	{
		InetAddress localhost = InetAddress.getLocalHost(); 
		System.out.println("Local IP: " + localhost.getHostAddress());
		listener= new ServerSocket(9090);
		socket = listener.accept();
		InputStream move = socket.getInputStream();
		
	}
	
	
}
