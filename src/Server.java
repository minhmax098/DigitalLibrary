import java.io.IOException;
import java.net.*;

public class Server {
	private DatagramSocket serverSocket;
	byte[] receiveData;
	DataHelper dh;
	
	Server(int port)
	{
		try {
			dh = new DataHelper();
			receiveData = new byte[1024];
			serverSocket = new DatagramSocket(port);
			System.out.println("Server is up!");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() throws IOException
	{	
		while(true)
		{
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			this.serverSocket.receive(receivePacket);
			//System.out.println("From client: " + new String(receivePacket.getData(), 0, receivePacket.getLength()));
			dh.updateDB(new String(receivePacket.getData(), 0, receivePacket.getLength()));
		}
	}

	public static void main(String args[]) throws Exception
	{
		Server sv = new Server(9876);
		sv.listen();
	}
}
