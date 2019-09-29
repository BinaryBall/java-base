import com.jamal.MyRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * rmi
 * 2019/9/27 15:39
 * client
 *
 * @author 曾小辉
 **/
public class Client {
    public static void main(String[] args) {
        new Client().go();
    }

    public void go(){
        try {
            Registry registry = LocateRegistry.getRegistry(2100);
            MyRemote service = (MyRemote) registry.lookup("hello");
//           MyRemote service = (MyRemote) Naming.lookup("rmi://localhost:10999/hello");
           System.out.println(service.hello());
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
