import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
public class Server5 extends PaxosServer{
	public Server5(int serverNumber) throws RemoteException {
		super(serverNumber);
	}
	public void main(String args[]) throws Exception{	
		try {
			int number = 5;
			Server5 server = new Server5(number);
			StorageInterface stub = (StorageInterface) 
		    		UnicastRemoteObject.exportObject(server, 0);
		    Registry registry = LocateRegistry.createRegistry(Parameters.PortofServer5); 
		    registry.bind(Parameters.SERVER5, stub);	
		    System.out.println("Paxos Server5 has already been spun up"+ " " 
		    		+ Parameters.DateTemplate.format(new Date().getTime()));
		} catch (Exception e) {
		    System.out.println("Server provisioning ran into issues : " + e.toString()
		    + Parameters.DateTemplate.format(new Date().getTime()));
		}
		
	    }
}