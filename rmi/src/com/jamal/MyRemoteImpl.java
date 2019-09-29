package com.jamal;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * rmi
 * 2019/9/27 16:17
 * impl
 *
 * @author 曾小辉
 **/
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

    private static final long serialVersionUID = -271947229644133464L;

    public MyRemoteImpl() throws RemoteException{

    }

    @Override
    public String hello() throws RemoteException {
        return "你好";
    }

    public static void main(String[] args) {
        try {
            MyRemote myRemote = new MyRemoteImpl();
//            LocateRegistry.createRegistry(10999); //加上此程序，就可以不要在控制台上开启RMI的注册程序，1099是RMI服务监视的默认端口
//            java.rmi.Naming.rebind("rmi://192.168.2.160:1099/hello", myRemote);
            Registry registry = LocateRegistry.createRegistry(2100);
            registry.bind("hello",myRemote);
            System.out.print("Ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
