As per my knowledge SCTP protocol is only working on unix machines.

Time Complexity of the algorithm is O(diameter of the graph) which is the distance between the farthest nodes.

For running the program (Required JRE 1.7 or greater and SCTP active on the machine)
----------------------------------------------------------------------------------------------
Place the NodeDiscovery.jar and config.txt at the same directory level and run the jar.
You need to edit config.txt as below.

# Number of nodes
-------------------------------
This wont be used anyways. You can give some random value here except -1.

# Node		Initial Knowledge
-------------------------------
<Source Node>  <adjacency list>

# Identifier	Hostname	Port
-------------------------------
<Source Node> <Hostname/IP> <Port on which SCTP is running>

Run runScript.sh to launch the program.
Run closeConnections.sh to close all the opened ports by killing the process associated with it.
