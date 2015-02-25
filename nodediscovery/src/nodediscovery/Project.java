package nodediscovery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Project{
	
	static HashMap<Integer, String> nodeDef = new HashMap<Integer, String>();
	static ArrayList<Integer> adjList = new ArrayList<>();
	static HashSet<Integer> listAllNodes = new HashSet<Integer>();
	static HashSet<Integer> terminate = new HashSet<Integer>();
	static int currentNode;
	
	public static void main(String[] ar){
		Project pro = new Project();
		currentNode = Integer.parseInt(ar[0]);
		pro.readFile();
		SctpServer srvr = new SctpServer(Integer.parseInt(nodeDef.get(currentNode).split(",")[1]));
		srvr.start();
		try {
			Thread.sleep(2000);
			sendMsg();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendMsg(){
		NodeInfo objNode = new NodeInfo(nodeDef.get(currentNode), adjList);
		for(Integer x: objNode.getList()){
			if(!listAllNodes.contains(x)){
				listAllNodes.add(x);
				terminate.add(x);
				//add node to new hashset
				String adrs = nodeDef.get(x);
				SctpClient.send(objNode, adrs.split(",")[0], Integer.parseInt(adrs.split(",")[1]));
			}
		}
	}
	
	public synchronized static void modifyList(NodeInfo obj){
		for(Integer x: obj.getList()){
			if(listAllNodes.contains(x));
			else{
				adjList.add(x);
				sendMsg();
			}
		}
		if(nodeDef.containsValue(obj.getValue())){
			for (Entry<Integer, String> entry : nodeDef.entrySet()) {
		        if (obj.getValue().equals(entry.getValue())) {
		            System.out.println("Node list discovered till now " + terminate.toString());
		            terminate.remove(entry.getKey());
		        }
		    }
		}
		if(terminate.size() == 2){
			System.out.println("Node discovery algorithm has completed successfully!!");
		}
	}
	
	public void readFile(){	    
	    	BufferedReader br;
	    	int nodes = -1;
			try {
				br = new BufferedReader(new FileReader("config.txt"));
				String line;
				while((line = br.readLine()) != null){
					if(line.length() == 0);
					else if(line.charAt(0) == '#');
					else{
						if(nodes == -1)
							nodes = Integer.parseInt(line);
						if(Integer.parseInt(line.charAt(0) + "") == currentNode){
							String list = line.substring(line.indexOf('{')+1, line.indexOf('}'));
							for(int i=0; i<list.length(); i++){
								if(list.charAt(i) == ' ' | list.charAt(i) == ',')
									i++;
								else
									adjList.add(Integer.parseInt(list.charAt(i)+""));
							}break;
						}
					}
				}
				while((line = br.readLine()) != null){
					if(line.length() == 0);
					else if(line.charAt(0) == '#');
					else if(line.charAt(0) == '0'){
						do{
						int key = Integer.parseInt(line.charAt(0)+"");
						String value;
						line = line.replaceAll("\\s+", ",");
						value = line.substring(2);
						nodeDef.put(key, value);
						}while((line = br.readLine()) != null);
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
