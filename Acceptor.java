import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.UUID;

public class Acceptor extends KeyValueStorage implements Runnable{

	private static int myproposalId;
	
	private boolean active;
	
	private int serverNumber;

	public int getMyproposalId() {
		return myproposalId;
	}

	public void setMyproposalId(int myproposalId) {
		Acceptor.myproposalId = myproposalId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isAlive) {
		this.active = isAlive;
	}
	
	public void start(){
		active = true;
	}
	
	public void kill(){
		active = false;
	}
	
	public boolean accept(int proposalId, UUID taskId, int action) throws RemoteException,
	SocketTimeoutException{
		return check(proposalId, taskId, action);
	}
	
	public boolean prepare(int proposalId, UUID taskId, int action) throws RemoteException,
	SocketTimeoutException{
		return check(proposalId, taskId, action);
	}

	private boolean check(int proposalId, UUID taskId, int action) throws RemoteException
	, SocketTimeoutException{
		//Randomly put a server to sleep
		/**try{
			if(((int)((Math.random()*Parameters.ServerTotal)+1)) == serverNumber){
				System.out.println("Server"+serverNumber+" is spinning down" 
			+ " per proposal " + proposalId + " " + Parameters.DateTemplate.format(new Date().getTime()));
				Thread.sleep(1);
			}
		} catch (InterruptedException ie){
			
		}**/
		if(proposalId < myproposalId){
			return false;
		}
			setMyproposalId(proposalId);
			System.out.println("Server"+serverNumber+" has acked the proposal and updated proposal Id: "
			+ myproposalId + " " + Parameters.DateTemplate.format(new Date().getTime()));
		return true;
	}

	public int getServerNumber() {
		return serverNumber;
	}

	public void setServerNumber(int serverNumber) {
		this.serverNumber = serverNumber;
	}

	@Override
	public void run() {
		
	}
}
