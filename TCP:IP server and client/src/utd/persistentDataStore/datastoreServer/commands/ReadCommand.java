package utd.persistentDataStore.datastoreServer.commands;

import java.io.File;
import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;


public class ReadCommand extends ServerCommand {
	byte[] msg;
	
public void run() throws IOException, ServerException{
	String cmdName = StreamUtil.readLine(inputStream);
	File file = new File(cmdName);
	if(file.exists()){
	    msg = FileUtil.readData(cmdName);
	    sendOK();
	    StreamUtil.writeLine(msg.length+"\n", outputStream);
	    StreamUtil.writeData(msg, outputStream);
	}
	else{
		sendError("File attempted to read was not found on Server");
		StreamUtil.writeLine("0", outputStream);
		StreamUtil.writeLine("null", outputStream);
	}
}

public String toString(){
	return "Read";
}
}
