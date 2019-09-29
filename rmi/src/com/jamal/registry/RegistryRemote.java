package com.jamal.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * rmi
 * 2019/9/27 19:58
 * registry 方法实现 rmi
 *
 * @author
 **/
public interface RegistryRemote extends Remote {

    String hello() throws RemoteException;
}
