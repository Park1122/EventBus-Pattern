/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

package Common.EventPackage;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class EventBusUtil {
    private long componentId = -1;
	private RMIEventBusInterface rmiEventBusInterface = null;
	
	public EventBusUtil() {
		if (componentId == -1) { 
		    try {
                rmiEventBusInterface = (RMIEventBusInterface) Naming.lookup("EventManager");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot find the EventManager.");
            }
		    
		    try {
		        componentId = rmiEventBusInterface.register();
            } catch (RemoteException e) {
                e.printStackTrace();
                System.out.println("Cannot be registered");
            }
		    
		} else {
			System.out.println("Already registered");
		}
	}

	/**
	 * 
	 * @param event
	 */
	public void sendEvent(Event event) {
		if (componentId != -1) {
		   	try {
				rmiEventBusInterface.sendEvent(event);
	    	} catch (Exception e) {
				System.out.println("Cannot send an event");
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public EventQueue getEventQueue() {
		EventQueue eventQueue = null;

		if (componentId != -1) {
	    	try {
				eventQueue = rmiEventBusInterface.getEventQueue(componentId);
	    	} catch(Exception e) {
				 System.out.println("Error getting an event queue.");
	    	}
	    } else {
			System.out.println("Not registered");
		}
		return eventQueue;
	}

	/**
	 */
	public void unRegister() {
		if (componentId != -1) {
		   	try {
				rmiEventBusInterface.unRegister(componentId);
		   	} catch (Exception e) {
				System.out.println("Error unregistering");
		    }
	    } else {
	        System.out.println("Not registered");
		}
	}
	
	public long getComponentId() {
		return this.componentId;
	}
}