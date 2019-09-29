package com.jamal.registry;

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
public class RegistryRemoteImpl extends UnicastRemoteObject implements RegistryRemote {

    public RegistryRemoteImpl() throws RemoteException {
    }

    @Override
    public String hello() throws RemoteException {
        return "我是 registry 方式实现的 rmi";
    }

    public static void main(String[] args) {
        try {
            RegistryRemote remote = new RegistryRemoteImpl();
            // 创建一个本地 rmiregistry 服务器
            Registry registry = LocateRegistry.createRegistry(1111);
            // 向 rmiregistry 注册
            registry.rebind("registry-hello", remote);

            System.out.println("注册完成......");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
