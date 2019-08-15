

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
public class KeyStoreClient {
	public static void main(String args[]){
		try {
			Registry registry = LocateRegistry.getRegistry(args[0], 
					Worker.getServerPort(args[1]));
			System.out.println(args[1]);
			StorageInterface stub = (StorageInterface) registry.lookup(args[1]);
			System.out.println(stub);
			HashMap<UUID, CrawlResult> request1 =
					new HashMap<>();
			HashMap<UUID, CrawlResult> request2 =
					new HashMap<>();
			UUID id1 = UUID.randomUUID();
			UUID id2 = UUID.randomUUID();
			CrawlTask crawlTask1 = new CrawlTask(id1, "www.amazon.com", "Amazon");
			CrawlTask crawlTask2 = new CrawlTask(id2, "www.google.com", "Google");
			CrawlResult result1 = new CrawlResult(crawlTask1,"www.amazon.com");
			CrawlResult result2 = new CrawlResult(crawlTask2,"www.google.com");
			System.out.println(crawlTask2.URL);
			System.out.println(stub);
			request1.put(id1, result1);
			request2.put(id2, result2);
			System.out.println(request2);
			System.out.println("CrawResult to process : "+Parameters.DateTemplate.format(new Date().getTime()) + " " 
					+ result1.toString());
			System.out.println(stub);
			HashMap<UUID, CrawlResult> response1 = stub.KeyValueStore(result1.crawlTask,result1.text,"Put");
			System.out.println(stub);
			HashMap<UUID, CrawlResult> response2 = stub.KeyValueStore(result2.crawlTask,result2.text,"Put");
			System.out.println(stub);
			String output1 = response1.get(id1).text;			
			String output2 = response2.get(id2).text;
			System.out.println("Server responsed for request1 : "+Parameters.DateTemplate.format(new Date().getTime()) + " " 
			+ output1);
			System.out.println("Server responsed for request2 : "+Parameters.DateTemplate.format(new Date().getTime()) + " " 
			+ output2);	
			String output3 = stub.KeyValueStore(result2.crawlTask,result2.text,"Delete").get(id2).text;
			System.out.println("Server response time : "+Parameters.DateTemplate.format(new Date().getTime()) + " " 
			+ output3);
	}
		catch(RemoteException re){
			System.out.println("Unable to find the RMI Server");
		} catch(NotBoundException ne){
			System.out.println("RMI Server not bound");
		}
	}
}
