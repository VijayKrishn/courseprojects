package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DeleteCommand extends ServerCommand{

public void run() throws IOException, ServerException{
	
	String cmdName = StreamUtil.readLine(inputStream);
	boolean check = FileUtil.deleteData(cmdName);
	if(check)
		sendOK();
	else
		sendError("File attempted to delete is not found on Server");
}

public String toString(){
	return "Delete";
}
}
