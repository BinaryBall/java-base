package com.jamal.naming;

import com.jamal.registry.RegistryRemote;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * rmi
 * 2019/9/27 20:09
 *
 * @author 曾小辉
 **/
public class NamingClient {
    public static void main(String[] args) {
        try {
            RegistryRemote remote = (RegistryRemote) Naming.lookup("rmi://127.0.0.1:2222/naming");
            System.out.println(remote.hello());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
