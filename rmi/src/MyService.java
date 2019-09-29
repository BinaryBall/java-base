import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * rmi
 * 2019/9/27 15:22
 * rmi service
 *
 * @author 曾小辉
 **/
public interface MyService extends Remote {
    // sya hello 方法
    String sayHello() throws RemoteException;
}
