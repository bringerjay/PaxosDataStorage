import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
public class Server4 extends PaxosServer{
	public Server4(int serverNumber) throws RemoteException {
		super(serverNumber);
	}
	public void main(String args[]) throws Exception{	
		try {
			int number = 4;
			Server4 server = new Server4(number);
			StorageInterface stub = (StorageInterface) 
		    		UnicastRemoteObject.exportObject(server, 0);
		    Registry registry = LocateRegistry.createRegistry(Parameters.PortofServer4); 
		    registry.bind(Parameters.SERVER4, stub);	
		    System.out.println("Paxos Server4 has already been spun up"+ " " 
		    		+ Parameters.DateTemplate.format(new Date().getTime()));
		} catch (Exception e) {
		    System.out.println("Server provisioning ran into issues : " + e.toString()
		    + Parameters.DateTemplate.format(new Date().getTime()));
		}
		
	    }
}