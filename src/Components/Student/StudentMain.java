package Components.Student;
/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

import Common.EventPackage.Event;
import Common.EventPackage.EventBusUtil;
import Common.EventPackage.EventId;
import Common.EventPackage.EventQueue;

import java.util.StringTokenizer;


/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class StudentMain {
	public static void main(String args[]) {
		EventBusUtil eventBusInterface = new EventBusUtil();
		Event event = null;
	    EventQueue eventQueue = null;
	    boolean done = false;
	    StudentComponent studentsList = null;
	    
	    if(eventBusInterface.getComponentId() != -1) {
	    	System.out.println("StudentMain (ID:" + eventBusInterface.getComponentId() + ") is successfully registered...");
	    } else {
	    	System.out.println("StudentMain is failed to register. Restart this component.");
	    }
	    
	    try {
	    	studentsList = new StudentComponent("Students.txt");
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    
	    while(!done) {
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

            eventQueue = eventBusInterface.getEventQueue();
            
            for(int i = 0; i < eventQueue.getSize(); i++) {
                event = eventQueue.getEvent();
                System.out.println("Received an event(ID: " + event.getEventId() + ")...");
                
                if(event.getEventId() == EventId.ListStudents) {    
                    String returnString = "";
                    for (int j = 0; j < studentsList.vStudent.size(); j++) {
                        returnString += (j == 0 ? "" : "\n") + ((Student) studentsList.vStudent.get(j)).toString();
                    }
                    
                    System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
                    System.out.println("\n ** Message: \n" + returnString + "\n  ...");
                    eventBusInterface.sendEvent(new Event(EventId.ClientOutput, returnString));
                }else if(event.getEventId() == EventId.RegisterStudents){
            		String studentInfo = event.getMessage();
        			System.out.println("Not null");
        			Student student = new Student(studentInfo);
                	if(studentsList.isRegisteredStudent(student.studentId) == false){
                		studentsList.vStudent.add(new Student(studentInfo));
                		System.out.println("A new student is successfully added...");
                		System.out.println("\"" + studentInfo + "\"");
                	} else{
                		System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
                        System.out.println("\n ** Message: This student is already registered.  ...");
                        eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "This student is already registered."));
                	}
            		
                }else if(event.getEventId() == EventId.DeleteStudents){
					String studentInfo = event.getMessage(); // 학생 ID가 event로 넘어오며, ID가 담기게 된다.
					if(studentsList.isRegisteredStudent(studentInfo)){ // 학생이 등록되어 있는 경우
						// 입력으로 받은 학생 ID와 일치하는 학생을 학생 목록에서 제거
						studentsList.vStudent.removeIf(s -> ((Student) s).studentId.equals(studentInfo));
						System.out.println("A student is successfully removed...");
						System.out.println("\"" + studentInfo + "\"");
					} else{
						System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
						System.out.println("\n ** Message: This student does not exist.  ...");
						eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "This student does not exist."));
					}

				}else if(event.getEventId() == EventId.ApplicationCheck){
					String applicationInfo = event.getMessage();
					String stID = applicationInfo.split(" ")[0];
					Student student = null;

					for(Object s : studentsList.vStudent){ // 입력한 학생 ID와 같은 ID를 가진 학생을 student 변수에 할당
						if(((Student) s).studentId.equals(stID)){
							student = (Student) s;
							break;
						}
					}

					if(student != null){
						eventBusInterface.sendEvent(new Event(EventId.ApplicationSuccessStudent, student.toString()));
						System.out.println("A student is successfully checked...");
						System.out.println("\"" + stID + "\"");
					}else{
						System.out.println("\n ** Sending an event(ID:" + EventId.ClientOutput + ") with");
						System.out.println("\n ** Message: This student does not exist.  ...");
						eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "This student does not exist."));
					}

				}else if(event.getEventId() == EventId.ApplicationSuccess){
					String studentAndCourses = event.getMessage();

					String stdId = studentAndCourses.split(" ")[0];
					String courseId = studentAndCourses.split(" ")[1];

					for (Object s : studentsList.vStudent) {
						// for문 진입 체크
						System.out.println("*******************Application Not if Statement in Test******************");
						if(((Student)s).studentId.equals(stdId)){ // 동일한 학생 ID의 학생의 수강리스트에 Course ID 추가
							((Student) s).completedCoursesList.add(courseId);
							System.out.println("*******************Application Success Test******************");
							break;
						}
					}

				}else if(event.getEventId() == EventId.QuitTheSystem) {
                    eventBusInterface.unRegister();
                    done = true;
                }
            }
        }
	    System.out.println("Shut down the component....");
	}
}
