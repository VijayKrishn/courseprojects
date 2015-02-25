package nodediscovery;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;

public class SctpClient
{
	public static final int MESSAGE_SIZE = 500;
	public static byte[] message;
	public static void go(String host, int port)
	{
		ByteBuffer byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
		try
		{
			SocketAddress socketAddress = new InetSocketAddress(host,port);
			SctpChannel sctpChannel = SctpChannel.open();
			//sctpChannel.bind(new InetSocketAddress(5500));
			sctpChannel.connect(socketAddress);
			MessageInfo messageInfo = MessageInfo.createOutgoing(null,0);
			byteBuffer.put(message);
			byteBuffer.flip();
			sctpChannel.send(byteBuffer,messageInfo);
			sctpChannel.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static byte[] send(NodeInfo obj, String host, int port){
		try {
			message = IO.serialize(obj);
			go(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
}