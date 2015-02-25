package utd.persistentDataStore.datastoreClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.StreamUtil;

public class ObjectstoreClientImpl extends DatastoreClientImpl implements ObjectstoreClient
{
	private static Logger logger = Logger.getLogger(ObjectstoreClientImpl.class);
	private InetAddress address;
	private int port;
	
	public ObjectstoreClientImpl(InetAddress address, int port)
	{
		super(address, port);
		this.address=address;
		this.port=port;
	}

    /* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.ObjectstoreClient#writeObject(java.lang.String, java.lang.Object)
	 */
    @Override
    public void writeObject(String name, Object object) throws ClientException
    {
		logger.debug("Executing WriteObject Operation");
		Socket client;
		try {
			client = new Socket(address, port);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//OutputStream outToServer = client.getOutputStream();
			//PrintStream out = new PrintStream(outToServer);
			ObjectOutputStream os = new ObjectOutputStream(baos);
			//out.print("writeobj\n");
			//out.print("name\n");
			
			os.writeObject(object);
			byte obj[] = baos.toByteArray();
			DatastoreClientImpl ds = new DatastoreClientImpl(address, port);
			ds.write(name, obj);
			//out.print(obj);
			os.close();
			InputStream inFromServer = client.getInputStream();
			logger.debug("Object writing execution Status : " +StreamUtil.readLine(inFromServer));
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.ObjectstoreClient#readObject(java.lang.String)
	 */
    @Override
    public Object readObject(String name) throws ClientException
    {
    	byte[] obj = null;
		logger.debug("Executing ReadObject Operation");
		Socket client;
		List<String> dataIn = null;
		try {
			client = new Socket(address, port);
			DatastoreClientImpl ds = new DatastoreClientImpl(address, port);
			InputStream inFromServer = client.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(inFromServer);
			logger.debug("Object writing execution Status : " +StreamUtil.readLine(inFromServer));
			obj = ds.read(name);
			dataIn = (List<String>)ois.readObject();
			/*OutputStream outToServer = client.getOutputStream();
			PrintStream out = new PrintStream(outToServer);
			
			out.print("readobj\n");
			out.print("name\n");*/
			//
			//dataIn = (List<String>)ois.readObject();
			logger.debug("Size of the OBJECT LIST is : " + dataIn.size());
			client.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataIn;
    }
    
    private static List<String> generateDataObj(int size)
	{
		List<String> data = new ArrayList<String>();
		for(int idx = 0; idx < size; idx++) {
			data.add("Now is the time for all good men " + idx);
		}
		return data;
	}
    
    public static void main(String[] args) {
		
		try {
			//DatastoreClientImpl ds = new DatastoreClientImpl(InetAddress.getLocalHost(),10023);
			ObjectstoreClientImpl osClient = new ObjectstoreClientImpl(InetAddress.getLocalHost(), 10023);
			osClient.write("vk.txt", DatastoreClientImpl.generateData(10));
			osClient.write("sasi.txt", DatastoreClientImpl.generateData(10));
			osClient.write("venky.txt", DatastoreClientImpl.generateData(10));
			osClient.read("rama.txt");
			//ds.delete("vk.txt");
			osClient.directory();
			osClient.delete("rama.txt");
			List<String> data = generateDataObj(100);
			osClient.writeObject("listData.txt", data);
			List<String> dataIn = (List<String>)osClient.readObject("listData");
		} catch (UnknownHostException | ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
