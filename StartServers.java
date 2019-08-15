import java.rmi.RemoteException;

public class StartServers {
	public static void main(String args[]) throws RemoteException{
		try {
			System.setProperty("sun.rmi.transport.tcp.responseTimeout", "10000");
			System.out.println(System.getProperties());
			Server1 server1 = new Server1(1);
			server1.main(null);
			Server2 server2 = new Server2(2);
			server2.main(null);
			Server3 server3 = new Server3(3);
			server3.main(null);
			Server4 server4 = new Server4(4);
			server4.main(null);
			Server5 server5 = new Server5(5);
			server5.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}