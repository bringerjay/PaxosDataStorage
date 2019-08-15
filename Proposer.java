import java.net.SocketTimeoutException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class Proposer extends KeyValueStorage implements Runnable{
	private static int proposalId;
	public int getProposalId() {
		return proposalId;
	}
	public Proposer(){
		super();
	}
	public void setProposalId(int proposalId) {
		Proposer.proposalId = proposalId;
	}	
	public void start(){
		proposalId = 0;
	}
	public synchronized CrawlResult propose(UUID taskId,CrawlResult crawResult, int action){
		CrawlResult response = new CrawlResult(); 
		Map<String, String> servers = Worker.getAllServers();
		System.out.println("Proposing putting");
		Registry registry = null;
		int count = 0;
		proposalId ++;
		try{
			for(Map.Entry<String, String> entry : servers.entrySet()){						
				try{
					registry = LocateRegistry.getRegistry(entry.getValue(), 
					Worker.getServerPort(entry.getKey()));
					StorageInterface stub = (StorageInterface) 
						registry.lookup(entry.getKey());
					System.out.println("prepare connecting " + registry);
					System.out.println(stub);
					if(stub.prepare(proposalId, taskId, action)){
						count ++;
					}	
				}catch(SocketTimeoutException se){
					System.out.println("1 server timed out");
					continue;
				}catch(RemoteException re){
					System.out.println("1 server timed out");
					continue;
				} 	
			}
			//Ensure at least 3 servers reply with ok
			if(count>(Parameters.ServerTotal/2)){
				System.out.println(count + " servers have consented to proceed.");
				count = 0;
				for(Map.Entry<String, String> entry : servers.entrySet()){					
					try{
						registry = LocateRegistry.getRegistry(entry.getValue(), 
								Worker.getServerPort(entry.getKey()));
						StorageInterface stub = (StorageInterface) 
								registry.lookup(entry.getKey());
						System.out.println("accept connecting " + registry);
						System.out.println(stub);
						//Communicate to all servers if they can accept the proposal
						if(stub.accept(proposalId, taskId, action)){
							count ++;
						}
					}catch(SocketTimeoutException se){
						System.out.println("Problems in committing");
						//Continue the process even if one server times out
						continue;
					}catch(RemoteException re){
						System.out.println("Problems in committing");
						//Continue the process even if one server was not reachable
						continue;
					} 	
				}
			} else {
				System.out.println("Consensus could not be reached as only " + count + " out of " +
						Parameters.ServerTotal +  
						"servers replied to the prepare request");
				return null;
			}
			//Ensure at least 3 servers reply with ok
			if(count>(Parameters.ServerTotal/2)){
				System.out.println(count + " servers replied with accept ok.  Paxos consensus reached.");
				for(Map.Entry<String, String> entry : servers.entrySet()){						
					try{
						registry = LocateRegistry.getRegistry(entry.getValue(), 
								Worker.getServerPort(entry.getKey()));
						StorageInterface stub = (StorageInterface) 
								registry.lookup(entry.getKey());
						System.out.println("commit connecting " + registry);
						System.out.println(stub);
						//Ask all servers to commit as quorum number has accepted
						response = stub.commit(taskId,crawResult, action);
						System.out.println("Got commit response " + response);
					}catch(SocketTimeoutException se){
						System.out.println("Problems in committing");
						//Continue the process even if one server times out
						continue;
					}catch(RemoteException re){
						System.out.println("Connection Problems in committing" + re);
						//Continue the process even if one server was not reachable
						continue;
					} 
				}
			} else {
				System.out.println("Consensus could not be reached as only " + count +
						"servers replied to the accept request");
				return response;
			}
		} 
		catch(NotBoundException nbe){
			System.out.println("Remote Exception" + nbe);
		}	
		System.out.println("Commit has succeeded");
		return response;
	}

	@Override
	public void run() {		
	}
}
