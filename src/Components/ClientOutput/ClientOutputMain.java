package Components.ClientOutput;
/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

import Common.EventPackage.Event;
import Common.EventPackage.EventBusUtil;
import Common.EventPackage.EventId;
import Common.EventPackage.EventQueue;


/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class ClientOutputMain {
    public static void main(String[] args) {
        EventBusUtil eventBusInterface = new EventBusUtil();
        EventQueue eventQueue = null;
        Event event = null;
        boolean done = false;
        ClientOutputComponent mainWindow = new ClientOutputComponent();
        
        if(eventBusInterface.getComponentId() != -1) {
	    	System.out.println("ClientOutputMain (ID:" + eventBusInterface.getComponentId() + ") is successfully registered...");
	    } else {
	    	System.out.println("ClientOutputMain is failed to register. Restart this component.");
	    }
        
        while(!done) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            eventQueue = eventBusInterface.getEventQueue();
            
            for(int i = 0; i < eventQueue.getSize(); i++) {
                event = eventQueue.getEvent();
                System.out.println("Received an event(ID: " + event.getEventId() + ")...");

                if(event.getEventId() == EventId.ClientOutput) {                    
                    mainWindow.printText(event.getMessage());
                } else if(event.getEventId() == EventId.ApplicationSuccess){ // 수강신청 성공
                    mainWindow.printText("Course application has been successfully applied.");
                } else if(event.getEventId() == EventId.QuitTheSystem) {
                    eventBusInterface.unRegister();
                    mainWindow.printText("Quit the system.");
                    done = true;
                }
            }
        }
        mainWindow.quitWindow();
    }
}
