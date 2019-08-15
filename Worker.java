import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Worker {
	
	
	public static Map<String, String> getAllServers(){
		Map<String, String> serverProperties = new HashMap<String, String>();
		try{
			Properties properties = new Properties();
			InputStream in = Worker.class.getResourceAsStream("hostIPs");
			properties.load(in);			
			Enumeration<?> p = properties.propertyNames();
			while (p.hasMoreElements()) {
				String name = (String) p.nextElement();
				String value = properties.getProperty(name);
				serverProperties.put(name, value);
			}
		} catch(FileNotFoundException fnfe){
			System.out.println("IP location file not found" + fnfe);
		} catch(IOException ioe){
			System.out.println("IOException thrown while reading the properties file" + ioe);
		}
		return serverProperties;
	}
	
	public static int getServerPort(String name) {
		if(name.equals(Parameters.SERVER1)){
			return Parameters.PortofServer1;
		}
		if(name.equals(Parameters.SERVER2)){
			return Parameters.PortofServer2;
		}
		if(name.equals(Parameters.SERVER3)){
			return Parameters.PortofServer3;
		}
		if(name.equals(Parameters.SERVER4)){
			return Parameters.PortofServer4;
		}
		if(name.equals(Parameters.SERVER5)){
			return Parameters.PortofServer5;
		}
		return 0;
	} 
}
