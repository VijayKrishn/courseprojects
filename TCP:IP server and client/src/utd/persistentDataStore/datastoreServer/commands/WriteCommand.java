package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;
import java.rmi.ServerException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand{
	
	public void run() throws IOException, ServerException{
		String fileName = StreamUtil.readLine(inputStream);
		System.out.println("into the write block" + fileName);
		int size = Integer.parseInt(StreamUtil.readLine(inputStream));
		byte[] data = StreamUtil.readData(size, inputStream);
		FileUtil.writeData(fileName, data);
		sendOK();
	}
	
	public String toString(){
		return "Write";
	}
}
