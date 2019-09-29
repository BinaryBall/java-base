import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * rmi
 * 2019/9/27 15:25
 * rmi myservice impl
 *
 * @author 曾小辉
 **/
public class MyServiceImpl extends UnicastRemoteObject implements MyService {

    private static final long serialVersionUID = -271947229644133464L;

//    public String sayHello() throws RemoteException {
//        return "你好！！！！！";
//    }

    @Override
    public String sayHello() throws RemoteException {
        return "哈哈";
    }

    public MyServiceImpl() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            MyService service = new MyServiceImpl();
            Naming.bind("rmi://127.0.0.1:1099/hello", service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
