package com.jamal.naming;

import com.jamal.registry.RegistryRemote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * rmi
 * 2019/9/27 19:59
 *
 * @author
 **/
public class NamingRemoteImpl extends UnicastRemoteObject implements NamingRemote {

    public NamingRemoteImpl() throws RemoteException {
    }

    @Override
    public String hello() throws RemoteException {
        return "我是 registry 方式实现的 rmi";
    }

    public static void main(String[] args) {
        try {
//            LocateRegistry.createRegistry(2222);
            NamingRemote remote = new NamingRemoteImpl();
            // 向 rmiregistry 注册
            Naming.rebind("rmi://192.168.1.94:2222/naming", remote);

            System.out.println("注册完成......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
