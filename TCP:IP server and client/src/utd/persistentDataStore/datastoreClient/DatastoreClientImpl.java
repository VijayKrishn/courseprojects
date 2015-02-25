package utd.persistentDataStore.datastoreClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.StreamUtil;

public class DatastoreClientImpl implements DatastoreClient
{
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	private InetAddress address;
	private int port;

	public DatastoreClientImpl(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.String, byte[])
	 */
	@Override
    public void write(String name, byte data[]) throws ClientException
	{
		logger.debug("Executing Write Operation");
		Socket client;
		try {
			client = new Socket(address, port);
			OutputStream outToServer = client.getOutputStream();
			PrintStream out = new PrintStream(outToServer);
			out.print("write\n");
			System.out.println("Writevijay");
			out.print(name + "\n");
			out.print(data.length + "\n");
			out.print(data + "\n");
			InputStream inFromServer = client.getInputStream();
			logger.debug("Write Execution Status : " +StreamUtil.readLine(inFromServer));
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.String)
	 */
	@Override
    public byte[] read(String name) throws ClientException
	{
		logger.debug("Executing Read Operation");
		Socket client;
		byte[] contents = null;
		try {
			client = new Socket(address, port);
			OutputStream outToServer = client.getOutputStream();
			PrintStream out = new PrintStream(outToServer);
			out.print("read\n");
			out.print(name + "\n");
			InputStream inFromServer = client.getInputStream();
			logger.debug("Read Execution Status : " +StreamUtil.readLine(inFromServer));
			int sizeByteArray = Integer.parseInt(StreamUtil.readLine(inFromServer));
			logger.debug("Size in the file : " + sizeByteArray);
			contents = StreamUtil.readData(sizeByteArray, inFromServer);
			logger.debug("Data Inside the file -- " + contents.toString());
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.String)
	 */
	@Override
    public void delete(String name) throws ClientException
	{
		logger.debug("Executing Delete Operation");
		Socket client;
		try {
			client = new Socket(address, port);
			OutputStream outToServer = client.getOutputStream();
			PrintStream out = new PrintStream(outToServer);
			out.print("delete\n");
			out.print(name + "\n");
			InputStream inFromServer = client.getInputStream();
			logger.debug("Deletion Execution Status : " +StreamUtil.readLine(inFromServer));
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
	 */
	@Override
    public List<String> directory() throws ClientException
	{
		logger.debug("Executing Directory Operation");
		Socket client;
		List<String> fileList = new ArrayList<>();
		int i = 0;
		try {
			client = new Socket(address, port);
			OutputStream outToServer = client.getOutputStream();
			PrintStream out = new PrintStream(outToServer);
			out.print("directory\n");
			InputStream inFromServer = client.getInputStream();
			logger.debug("Directory Execution Status : " +StreamUtil.readLine(inFromServer));
			int numOfFiles = Integer.parseInt(StreamUtil.readLine(inFromServer));
			logger.debug("Number of files : " + numOfFiles);
			while(numOfFiles > 0){
				fileList.add(StreamUtil.readLine(inFromServer));
				logger.debug("File " + (i+1) + " : " + fileList.get(i));
				numOfFiles--;
				i++;
			}
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileList;
	}
	
	protected static byte[] generateData(int size)
	{
		byte data[] = new byte[size];
		Random random = new Random();
		random.nextBytes(data);
		return data;
	}

}
