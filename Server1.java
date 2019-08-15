import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
public class Server1 extends PaxosServer{
	public Server1(int serverNumber) throws RemoteException {
		super(serverNumber);
	}
	public void main(String args[]) throws Exception{	
		try {
			int number = 1;
			Server1 server = new Server1(number);
			StorageInterface stub = (StorageInterface) 
		    		UnicastRemoteObject.exportObject(server, 0);
		    Registry registry = LocateRegistry.createRegistry(Parameters.PortofServer1); 
		    registry.bind(Parameters.SERVER1, stub);	
		    System.out.println("Paxos Server1 has already been spun up"+ " " 
		    		+ Parameters.DateTemplate.format(new Date().getTime()));
		} catch (Exception e) {
		    System.out.println("Server provisioning ran into issues : " + e.toString()
		    + Parameters.DateTemplate.format(new Date().getTime()));
		}
		
	    }
}
