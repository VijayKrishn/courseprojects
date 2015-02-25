package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;
import java.util.List;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

import utd.persistentDataStore.utils.FileUtil;

public class DirectoryCommand extends ServerCommand{

public void run() throws IOException, ServerException{
	
	List<String> files = FileUtil.directory();
	String count = files.size()+"\n";
	sendOK();
	StreamUtil.writeLine(count, outputStream);
	for(int i=0; i<files.size(); i++){
		StreamUtil.writeLine(files.get(i)+"\n", outputStream);
	}
	}

public String toString(){
	return "Directory";
}
}
