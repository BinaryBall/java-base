package com.jamal;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * rmi
 * 2019/9/27 16:12
 * myremote
 *
 * @author 曾小辉
 **/
public interface MyRemote extends Remote {

    String hello() throws RemoteException;
}
