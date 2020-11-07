package Components.Register;

import Common.EventPackage.Event;
import Common.EventPackage.EventBusUtil;
import Common.EventPackage.EventId;
import Common.EventPackage.EventQueue;

public class RegisterMain {
    public static void main(String[] args) {
        EventBusUtil eventBusInterface = new EventBusUtil();
        Event event = null;
        EventQueue eventQueue = null;
        boolean done = false;

        if(eventBusInterface.getComponentId() != -1) {
            System.out.println("RegisterMain (ID:" + eventBusInterface.getComponentId() + ") is successfully registered...");
        } else {
            System.out.println("RegisterMain is failed to register. Restart this component.");
        }

        String studentInfo = null, courseInfo = null;
        while(!done) {
            eventQueue = eventBusInterface.getEventQueue();

            for(int i = 0; i < eventQueue.getSize(); i++) {
                event = eventQueue.getEvent();
                System.out.println("Received an event(ID: " + event.getEventId() + ")...");

                if(event.getEventId() == EventId.ApplicationSuccessStudent) {
                    studentInfo = event.getMessage(); // 이벤트의 메시지에 담긴 학생 정보를 변수에 할당
                }else if(event.getEventId() == EventId.ApplicationSuccessCourse) {
                    courseInfo = event.getMessage(); // 이벤트의 메시지에 담긴 Course 정보를 변수에 할당
                }else if(event.getEventId() == EventId.QuitTheSystem) {
                    eventBusInterface.unRegister(); // 종료
                    done = true;
                }

                if(studentInfo!=null && courseInfo!=null) { // 학생 정보와 Course 정보가 성공적으로 받아진 경우
                    String[] courseInfoArray = courseInfo.split(" "); // Course 정보를 띄어쓰기를 기준으로 나누어 배열에 담기
                    boolean prerequisiteOK = true;

                    for(int x = 0; x< courseInfoArray.length-3; x++) { // 선수과목 부분만 비교하기 위해 -3만큼 인덱스 줄이기, 안줄이면 인덱스 넘어감
                        if (!studentInfo.contains(courseInfoArray[x + 3])) { // 선수과목 부분 코드 부분만 비교
                            prerequisiteOK = false;
                            break;
                        }
                    }

                    if(!prerequisiteOK) { // 선수과목 이수를 하지 않은 경우 실패
                        System.out.println("Failed to register for the class. Not completed prerequisite courses.  ...");
                        eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "Register Course Fail. Not completed prerequisite courses."));
                    } else if(studentInfo.contains(courseInfoArray[0])) { // 이미 들은 과목인 경우 실패
                        System.out.println("Failed to register for the class. Already taken this course.  ...");
                        eventBusInterface.sendEvent(new Event(EventId.ClientOutput, "Failed to register for the class. Already taken this course."));
                    }else { // 성공적으로 수강신청을 한 경우
                        System.out.println("Register Success.  ..."+ studentInfo+" "+ courseInfoArray[0]);
                        eventBusInterface.sendEvent(new Event(EventId.ApplicationSuccess, studentInfo.split(" ")[0] + " " + courseInfoArray[0]));
                    }

                    studentInfo = null;
                    courseInfo = null;
                }else{ // 학생 정보 또는 Course 정보가 받아오지 못한 경우
                    System.out.println("studentInfo = " + (studentInfo == null));
                    System.out.println("courseInfo = " + (courseInfo == null));
                }
            }
        }
        System.out.println("Shut down the component....");
    }
}
