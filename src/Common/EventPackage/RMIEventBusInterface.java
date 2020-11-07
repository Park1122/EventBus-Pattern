/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

package Common.EventPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public interface RMIEventBusInterface extends Remote {
	public long register() throws RemoteException;
	public void unRegister(long SenderID) throws RemoteException;
	public void sendEvent(Event m ) throws RemoteException;
	public EventQueue getEventQueue(long SenderID) throws RemoteException;
}
