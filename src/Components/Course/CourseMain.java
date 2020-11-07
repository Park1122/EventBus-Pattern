package Components.Course;
/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

import Common.EventPackage.Event;
import Common.EventPackage.EventBusUtil;
import Common.EventPackage.EventId;
import Common.EventPackage.EventQueue;
import Components.Student.Student;

import java.util.StringTokenizer;


/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class CourseMain {
    public static void main(String[] args) {
        EventBusUtil eventBusInterface = new EventBusUtil();
        Event event = null;
        EventQueue eventQueue = null;
        boolean done = false;
        CourseComponent coursesList = null;
        
        if(eventBusInterface.getComponentId() != -1) {
	    	System.out.println("CourseMain (ID:" + eventBusInterface.getComponentId() + ") is successfully registered...");
	    } else {
	    	System.out.println("CourseMain is failed to register. Restart this component.");
	    }
	    
        try {
            coursesList = new CourseComponent("Courses.txt");
        } catch (Exception e) {
            e.printStackTrace();
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
                
                if(event.getEventId() == EventId.ListCourses) {
                    String returnString = "";
                    for(int j = 0; j < coursesList.vCourse.size(); j++) {
                        returnString += ((Course) coursesList.vCourse.get(j)).toString() + "\n";
                    }
                    
                    System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
                    System.out.println("\n ** Message: \n" + returnString + "\n  ...");
                    eventBusInterface.sendEvent(new Event(EventId.ClientOutput, returnString));
                    
                } else if(event.getEventId() == EventId.RegisterCourses) {
                	String courseInfo = event.getMessage();
                	Course course = new Course(courseInfo);
                    if(coursesList.isRegisteredCourse(course.courseId) == false){
                        coursesList.vCourse.add(new Course(courseInfo));
                        System.out.println("A new course is successfully added...");
                		System.out.println("\"" + courseInfo + "\"");
                    } else{
                    	System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
                        System.out.println("\n ** Message: This course is already registered.  ...");
                        eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "This course is already registered."));
                    }

                }else if(event.getEventId() == EventId.DeleteCourses) {
                    String courseInfo = event.getMessage();
                    // Course가 있는지 확인
                    if(coursesList.isRegisteredCourse(courseInfo)){
                        // 입력받은 Course ID를 가진 Course를 목록에서 삭제
                        coursesList.vCourse.removeIf(c -> ((Course) c).courseId.equals(courseInfo));
                        System.out.println("A course is successfully removed...");
                        System.out.println("\"" + courseInfo + "\"");
                    } else{
                        System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
                        System.out.println("\n ** Message: This course does not exist.  ...");
                        eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "This course does not exist."));
                    }

                }else if(event.getEventId() == EventId.ApplicationCheck) {
                    String applicationInfo = event.getMessage();
                    StringTokenizer st = new StringTokenizer(applicationInfo);
                    String cID = applicationInfo.split(" ")[1];
                    Course course = null;

                    for (Object c : coursesList.vCourse) { // 입력한 Course ID와 같은 ID를 가진 Course를 course 변수에 할당
                        if (((Course) c).courseId.equals(cID)) {
                            course = (Course) c;
                            break;
                        }
                    }

                    if (course != null) {
                        eventBusInterface.sendEvent(new Event(EventId.ApplicationSuccessCourse, course.toString()));
                        System.out.println("A course is successfully checked...");
                        System.out.println("\"" + cID + "\"");
                    }else{
                        System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
                        System.out.println("\n ** Message: This course does not exist.  ...");
                        eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "This course does not exist."));
                    }

                } else if(event.getEventId() == EventId.QuitTheSystem) {
                    eventBusInterface.unRegister();
                    done = true;
                }
            }
        }     
        System.out.println("Shut down the component....");
    }
}
