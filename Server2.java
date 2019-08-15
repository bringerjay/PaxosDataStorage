import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
public class Server2 extends PaxosServer{
	public Server2(int serverNumber) throws RemoteException {
		super(serverNumber);
	}
	public void main(String args[]) throws Exception{	
		try {
			int number = 2;
			Server2 server = new Server2(number);
			StorageInterface stub = (StorageInterface) 
		    		UnicastRemoteObject.exportObject(server, 0);
		    Registry registry = LocateRegistry.createRegistry(Parameters.PortofServer2); 
		    registry.bind(Parameters.SERVER2, stub);	
		    System.out.println("Paxos Server2 has already been spun up"+ " " 
		    		+ Parameters.DateTemplate.format(new Date().getTime()));
		} catch (Exception e) {
		    System.out.println("Server provisioning ran into issues : " + e.toString()
		    + Parameters.DateTemplate.format(new Date().getTime()));
		}
		
	    }
}