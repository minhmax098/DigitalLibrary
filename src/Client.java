import java.io.IOException;
import java.net.*;

public class Client {
	private DatagramSocket clientSocket;
	private int port;
	private InetAddress IPAddress;
	private byte[] sendData = new byte[1024];
	
	public Client() throws SocketException
	{
		try {
			this.port = 9876;
			this.clientSocket = new DatagramSocket();
			this.IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(String query) throws IOException
	{
		System.out.println(query);
		sendData = query.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		
		this.clientSocket.send(sendPacket);		
		//System.out.println("Client sent!");
	}
	
	public void close()
	{
		this.clientSocket.close();
	}
}
