import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;


public class KeyValueStorage {
	private HashMap<UUID, CrawlResult> storage =
			new HashMap<>();
	
	public KeyValueStorage(){
	}	
	public CrawlResult getKey(UUID taskId) {
		System.out.println("Reading");
		CrawlResult response  = storage.get(taskId);
		System.out.println("Retrieved player reocord : " + taskId + " " + response.toString() + " to the store at : " 
		+ Parameters.DateTemplate.format(new Date().getTime()));
		return response;
	}
		public CrawlResult putKeyValue(UUID taskId, CrawlResult crawlResult) {
			System.out.println("Putting");
		storage.put(taskId, crawlResult);
		CrawlResult response = storage.get(taskId);
		System.out.println("Added player reocord : " + taskId + " " + crawlResult.toString() + " to the store at : " 
		+ Parameters.DateTemplate.format(new Date().getTime()));
		return response;
	}
	
	public CrawlResult deleteKey(UUID taskId) {
		System.out.println("Deleting");
		CrawlResult response  = storage.get(taskId);
		storage.remove(taskId);
		System.out.println(taskId + " : " + response.toString() +" record is removed at :" 
		        	+ Parameters.DateTemplate.format(new Date().getTime()));
	    return response;
	}	
}
