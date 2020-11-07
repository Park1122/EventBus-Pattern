package Framework;


import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import Common.EventPackage.Event;
import Common.EventPackage.EventQueue;
import Common.EventPackage.RMIEventBusInterface;


/**
 * @author Hwi Ahn, Pilsu Jung, Jungho Kim
 * @date 2013-08-07
 * @version 1.0
 * @description
 *      RMI 메세지 서버 클래스. 각 컴포넌트들에 대한 EventQueue 객체를 갖고 있고 Event 전송 요청이 왔을 때 해당 Event를 모든 EventQueue 객체에 추가하여준다.
 */
public class EventManager extends UnicastRemoteObject implements RMIEventBusInterface {
    private static final long serialVersionUID = 1L; //Default value
    
    static Vector<EventQueue> eventQueueList;

	/**
	 * Constructor. 초기 설정으로써 EventQueue를 15개 넣을 수 있는 Vector 를 선언한다.
	 * @throws RemoteException
	 */
	public EventManager() throws RemoteException {
		super();
		eventQueueList = new Vector<EventQueue>(15, 1);
	}
	
	public static void main(String args[]) {
		try {
			EventManager eventBus = new EventManager();
	      	Naming.bind("EventManager", eventBus); // Java에서 분산(멀티) 프로세싱을 위해 사용
		} catch (Exception e) {
			System.out.println("Event bus startup error: " + e);
		}
		
		System.out.println("Event Bus is running now...");
	}

	/* (non-Javadoc)
	 * @see Common.EventPackage.RMIEventBusInterface#register()
	 * 새로 등록되는 컴포넌트를 위한 EventQueue를 선언하고 해당 EventQueue를 리스트에 추가한다.
	 */
	synchronized public long register() throws RemoteException {

		EventQueue newEventQueue = new EventQueue();
		eventQueueList.add( newEventQueue );
		
		System.out.println("Component (ID:"+ newEventQueue.getId() + ") is registered...");
		return newEventQueue.getId();
	}
	
	/* (non-Javadoc)
	 * @see Common.EventPackage.RMIEventBusInterface#unRegister(long)
	 * 기존의 컴포넌트를 등록해제하고 EventQueue를 삭제한다.
	 */
	synchronized public void unRegister(long id) throws RemoteException {
		EventQueue eventQueue;

		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			eventQueue =  eventQueueList.get(i);
			
			if (eventQueue.getId() == id) {
				eventQueue = eventQueueList.remove(i);
				System.out.println("Component (ID:"+ id + ") is unregistered...");
			}
		}
	}

	/* (non-Javadoc)
	 * @see Common.EventPackage.RMIEventBusInterface#sendEvent(Common.EventPackage.Event)
	 * Event를 전송한다. 해당 Event를 모든 EventQueue에 넣어준다.
	 */
	synchronized public void sendEvent(Event sentEvent) throws RemoteException {
		EventQueue eventQueue;

		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			eventQueue = eventQueueList.get(i);
			eventQueue.addEvent(sentEvent);
			eventQueueList.set(i, eventQueue);
		}
		System.out.println("Sending an event of which ID is " + sentEvent.getEventId() + ".");
	}

	/* (non-Javadoc)
	 * @see Common.EventPackage.RMIEventBusInterface#getEventQueue(long)
	 * 특정 컴포넌트의 EventQueue를 반환한다.
	 */
	synchronized public EventQueue getEventQueue(long id) throws RemoteException {
		EventQueue originalQueue = null; 
		EventQueue copiedQueue =  null;

		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			originalQueue =  eventQueueList.get(i);

			if (originalQueue.getId() == id) {
				originalQueue = eventQueueList.get(i);
				copiedQueue = originalQueue.getCopy();
				originalQueue.clearEventQueue();
			}
		}

		return copiedQueue;
	}
}