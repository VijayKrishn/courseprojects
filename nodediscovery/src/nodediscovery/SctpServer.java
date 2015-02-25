package nodediscovery;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

public class SctpServer extends Thread
{
	public static final int MESSAGE_SIZE = 500;
	public static int PORT;
	
	public SctpServer(int port){
		PORT = port;
	}
	public void go()
	{
		ByteBuffer byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
		try
		{
			SctpServerChannel sctpServerChannel = SctpServerChannel.open();
			InetSocketAddress serverAddr = new InetSocketAddress(PORT);
			sctpServerChannel.bind(serverAddr);
			while(true)
			{
				SctpChannel sctpChannel = sctpServerChannel.accept();
				MessageInfo messageInfo = sctpChannel.receive(byteBuffer,null,null);
				System.out.println(messageInfo);
				byte[] message1 = byteToString(byteBuffer);
				NodeInfo obj = (NodeInfo) IO.deserialize(message1);
				Project.modifyList(obj);
				sctpChannel.close();
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		go();
	}

	public byte[] byteToString(ByteBuffer byteBuffer)
	{
		byteBuffer.position(0);
		byteBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[byteBuffer.remaining()];
		byteBuffer.get(bufArr);
		return bufArr;
	}

}