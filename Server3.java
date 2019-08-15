import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
public class Server3 extends PaxosServer{
	public Server3(int serverNumber) throws RemoteException {
		super(serverNumber);
	}
	public void main(String args[]) throws Exception{	
		try {
			int number = 3;
			Server3 server = new Server3(number);
			StorageInterface stub = (StorageInterface) 
		    		UnicastRemoteObject.exportObject(server, 0);
		    Registry registry = LocateRegistry.createRegistry(Parameters.PortofServer3); 
		    registry.bind(Parameters.SERVER3, stub);	
		    System.out.println("Paxos Server3 has already been spun up"+ " " 
		    		+ Parameters.DateTemplate.format(new Date().getTime()));
		} catch (Exception e) {
		    System.out.println("Server provisioning ran into issues : " + e.toString()
		    + Parameters.DateTemplate.format(new Date().getTime()));
		}
		
	    }
}