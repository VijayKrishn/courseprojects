package nodediscovery;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7551926138045709758L;
	String host;
	String value;
	
	public String getValue(){
		return value;
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public synchronized ArrayList<Integer> getList() {
		return list;
	}

	int port;
	ArrayList<Integer> list = new ArrayList<Integer>();
	
	public NodeInfo(String host, ArrayList<Integer> list){
		value = host;
		String arr[] = host.split(",");
		this.host = arr[0];
		port = Integer.parseInt(arr[1]);
		this.list = list;
	}
}
