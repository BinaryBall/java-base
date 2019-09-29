package com.jamal.registry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * rmi
 * 2019/9/27 20:09
 *
 * @author 曾小辉
 **/
public class RegistryClient {
    public static void main(String[] args) {
        try {
            // 获取到本地的 rmi 服务器
            Registry registry = LocateRegistry.getRegistry(1111);
            RegistryRemote remote = (RegistryRemote) registry.lookup("registry-hello");
            System.out.println(remote.hello());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
