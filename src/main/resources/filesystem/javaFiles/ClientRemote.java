


import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;



public class ClientRemote
{
    public static void main(String arg[])
    {
        // I download server's stubs ==> must set a SecurityManager
        //System.setSecurityManager(new RMISecurityManager()); 
        
        try
        {
        	Registry registry = LocateRegistry.getRegistry("127.0.0.1",1304);
        	HelloRemote obj = (HelloRemote) registry.lookup("Server"); 
             //objectname in registry
          
           System.out.println(obj.sayHello());
        }
        catch (Exception e)
        {
           System.out.println("HelloClient exception: " + e.getMessage());
           e.printStackTrace();
        }
    }
}
  