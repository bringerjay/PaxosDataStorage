import java.util.HashMap;
import java.util.UUID;

//Learner is responsible to maintain the state of the server and handles the commits to it
public class Learner extends KeyValueStorage implements Runnable{

	public void start(){
		
	}	
	public CrawlResult commit(UUID taskId,CrawlResult crawResult, int action){
		System.out.println("Learner received request " + action);
		CrawlResult response = new CrawlResult();
		// If the consensus is met then go ahead and perform the commit
		switch(action) {
			case 1: 				
				System.out.println("Learner committing Getting");
				response = super.getKey(taskId);
					break;
			case 2: 
				System.out.println("Learner committing Putting");
				response = super.putKeyValue(taskId,crawResult);
					break;
			case 3: 
				System.out.println("Learner committing Deleting");
				response = super.deleteKey(taskId);
					break;
			default:
				System.out.println("action # did not match");
				  return null;
		}
		return response;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub	
	}
}
