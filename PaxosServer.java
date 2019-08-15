

import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;


public class PaxosServer implements StorageInterface{
	private Proposer proposer;
	private Learner learner;
	private Acceptor acceptor;
	public PaxosServer(int serverNumber) throws RemoteException {
		proposer = new Proposer();
		learner = new Learner();
		acceptor = new Acceptor();
		proposer.start();
		learner.start();
		acceptor.start();
		acceptor.setServerNumber(serverNumber);
	}
	private CrawlResult get(UUID taskId){
		return proposer.propose(taskId,null, 1);
	}	
	private CrawlResult put(UUID taskId, CrawlResult crawResult){
		
		return proposer.propose(taskId,crawResult,2);
	}	
	private CrawlResult delete(UUID taskId){

		return proposer.propose(taskId,null,3);
	}
	@Override
	public boolean prepare(int proposalId, UUID taskId, int action)
			throws RemoteException, SocketTimeoutException {
		return acceptor.prepare(proposalId, taskId, action);
	}
	
	@Override
	public boolean accept(int proposalId, UUID taskId, int action) 
			throws RemoteException, SocketTimeoutException  {
		return acceptor.accept(proposalId, taskId, action);
	}

	@Override
	public CrawlResult commit(UUID taskId, CrawlResult crawResult, int action) 
			throws RemoteException, SocketTimeoutException  {
		return learner.commit(taskId,crawResult,action);			
	}

	@Override
	public HashMap<UUID, CrawlResult> KeyValueStore(CrawlTask crawlTask, String text, String functionality)
			throws RemoteException {
		UUID taskId = crawlTask.taskId;
		HashMap<UUID, CrawlResult> response = new HashMap<UUID, CrawlResult>(); 
		  CrawlResult crawResult = new CrawlResult(crawlTask,text);
		switch(functionality) {
		  case "Get":
				System.out.println("Committing Getting");
			  crawResult = null;
			  crawResult = get(taskId);
			  response.put(taskId,crawResult);		 
			  return response;			  
		  case "Put":
				System.out.println("Committing Putting");
				System.out.println(crawlTask.keyword + crawlTask.URL);
				System.out.println(text);
				System.out.println(taskId);
			  crawResult = put(taskId,crawResult);
			  response.put(taskId,crawResult);	
				System.out.println(response);
			  return response;
		  case "Delete":
				System.out.println("Committing Deleting");
			  crawResult = delete(taskId);
			  response.put(taskId,crawResult);		 
			  return response;
		  default:
			  return null;		}	
	}
}
