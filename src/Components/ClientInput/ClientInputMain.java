package Components.ClientInput;
/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Common.EventPackage.EventId;

/**
 *  
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class ClientInputMain {
    public static void main(String[] args) {
        ClientInputComponent clientInputComponent = new ClientInputComponent();
        
        if(clientInputComponent.registered) {
        	System.out.println("ClientInputMain (ID:" + clientInputComponent.getComponentId() + ") is successfully registered...");
            clientInputComponent.start();

            while(true) {
                //Menu
                System.out.println( "Registeration System\n" );
                System.out.println("1. List Students");
                System.out.println("2. List Courses");
                System.out.println("3. Register a new Student");
                System.out.println("4. Register a new Course");
                System.out.println("5. Delete a Student");
                System.out.println("6. Delete a Course");
                System.out.println("7. Applicate a Course");
                System.out.println("\n 0. Quit the system");
                
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                
                try {
                    String selection = br.readLine().trim();

                    if(selection.equals("1")) {
                        clientInputComponent.sendClientInput(EventId.ListStudents, null);

                    } else if(selection.equals("2")) {
                        clientInputComponent.sendClientInput(EventId.ListCourses, null);

                    } else if(selection.equals("3")) {
                    	String msg = "";
                    	boolean inputIsDone = false;
                    	while(!inputIsDone) {
                    		System.out.println("\nEnter student ID and press return (Ex. 20131234)>> ");
                            String sSID = br.readLine().trim();
                            System.out.println("\nEnter family name and press return (Ex. Hong)>> ");
                            String firstName = br.readLine().trim();
                            System.out.println("\nEnter first name and press return (Ex. Gildong)>> ");
                            String lastName = br.readLine().trim();
                            System.out.println("\nEnter department and press return (Ex. CS)>> ");
                            String dep = br.readLine().trim();
                            System.out.println("\nEnter a list of IDs (put a space between two different IDs) of the completed courses and press return >> ");
                            System.out.println("(Ex. 17651 17652 17653 17654)");
                            String completedCourse = br.readLine().trim();
                            msg = sSID + " " + lastName + " " + firstName + " " + dep + " " + completedCourse;

                            while(true) {
                            	System.out.println("\nIs it correct information? (Y/N)");
                                System.out.println(msg);
                                String ans = br.readLine().trim();
                                if(ans.equalsIgnoreCase("y")) {
                                	inputIsDone = true;
                                	break;
                                } else if(ans.equalsIgnoreCase("n")) {
                                	System.out.println("\nType again...");
                                	msg = "";
                                	break;
                                } else {
                                	System.out.println("\nTyped wrong answer");
                                }
                            }
                    	}

                    	System.out.println("\n ** Sending an event(ID:" + EventId.RegisterStudents + ") with ");
                    	System.out.println("\n ** Message \"" + msg + "\" ...");
                    	clientInputComponent.sendClientInput(EventId.RegisterStudents, msg);

                    } else if(selection.equals("4")) {
                        String userInput = "";
                        boolean inputIsDone = false;

                        while(!inputIsDone) {
                        	System.out.println("\nEnter course ID and press return (Ex. 12345)>> ");
                            userInput = br.readLine().trim();
                            System.out.println("\nEnter the family name of the instructor and press return (Ex. Hong)>> ");
                            userInput += " " + br.readLine().trim();
                            System.out.println("\nEnter the name of the course ( substitute a space with ab underbar(_) ) and press return (Ex. C++_Programming)>> ");
                            userInput += " " + br.readLine().trim();
                            System.out.println("\nEnter a list of IDs (put a space between two different IDs) of prerequisite courses and press return >> ");
                            System.out.println("(Ex. 12345 17651)");
                            userInput += " " + br.readLine().trim();

                            while(true) {
                            	System.out.println("\nIs it correct information? (Y/N)");
                                System.out.println(userInput);
                                String ans = br.readLine().trim();
                                if(ans.equalsIgnoreCase("y")) {
                                	inputIsDone = true;
                                	break;
                                } else if(ans.equalsIgnoreCase("n")) {
                                	System.out.println("\nType again...");
                                	userInput = "";
                                	break;
                                } else {
                                	System.out.println("\nTyped wrong answer");
                                }
                            }
                        }

                        System.out.println("\nSending an event(ID:" + EventId.RegisterCourses + ") with");
                        System.out.println("\n ** Message \"" + userInput + "\" ...");
                        clientInputComponent.sendClientInput(EventId.RegisterCourses, userInput);

                    }else if(selection.equals("5")) {
                        String msg = "";
                        boolean inputIsDone = false;
                        while(!inputIsDone) { // input이 끝이 안난 동안
                            System.out.println("\nEnter student ID and press return (Ex. 20131234)>> ");
                            String sSID = br.readLine().trim(); // 입력된 학생ID를 받는다.
                            msg = sSID; // 이를 메시지 변수에 저장

                            while(true) {
                                System.out.println("\nIs it correct information? (Y/N)");
                                System.out.println(msg);
                                String ans = br.readLine().trim();
                                // 입력이 끝났는지 체크하는 부분
                                // y입력시 입력 종료, n입력시 입력 다시
                                if(ans.equalsIgnoreCase("y")) {
                                    inputIsDone = true;
                                    break;
                                } else if(ans.equalsIgnoreCase("n")) {
                                    System.out.println("\nType again...");
                                    msg = "";
                                    break;
                                } else {
                                    System.out.println("\nTyped wrong answer");
                                }
                            }
                        }

                        // 학생 ID가 할당된 msg변수를 이벤트를 통해 전달
                        System.out.println("\n ** Sending an event(ID:" + EventId.DeleteStudents + ") with ");
                        System.out.println("\n ** Message \"" + msg + "\" ...");
                        clientInputComponent.sendClientInput(EventId.DeleteStudents, msg);

                    }else if(selection.equals("6")) {
                        String msg = "";
                        boolean inputIsDone = false;
                        while(!inputIsDone) {
                            System.out.println("\nEnter Course ID and press return (Ex. 12345)>> ");
                            String sSID = br.readLine().trim();
                            msg = sSID;

                            while(true) {
                                System.out.println("\nIs it correct information? (Y/N)");
                                System.out.println(msg);
                                String ans = br.readLine().trim();
                                if(ans.equalsIgnoreCase("y")) {
                                    inputIsDone = true;
                                    break;
                                } else if(ans.equalsIgnoreCase("n")) {
                                    System.out.println("\nType again...");
                                    msg = "";
                                    break;
                                } else {
                                    System.out.println("\nTyped wrong answer");
                                }
                            }
                        }
                        
                        // Course ID가 할당된 msg를 이벤트를 통해 전달
                        System.out.println("\n ** Sending an event(ID:" + EventId.DeleteCourses + ") with ");
                        System.out.println("\n ** Message \"" + msg + "\" ...");
                        clientInputComponent.sendClientInput(EventId.DeleteCourses, msg);

                    } else if(selection.equals("7")) {
                        String msg = "";
                        boolean inputIsDone = false;
                        while(!inputIsDone) {
                            System.out.println("\nEnter student ID and press return (Ex. 20100123)>> ");
                            String sID = br.readLine().trim(); // 입력받은 학생 ID를 sID 변수에 할당
                            System.out.println("\nEnter course ID and press return (Ex. 12345)>> ");
                            String cID = br.readLine().trim(); // 입력받은 Course ID를 cID 변수에 할당

                            msg = sID + " " + cID; // 띄어쓰기를 기준으로 학생 ID와 Course ID를 전달

                            while(true) {
                                System.out.println("\nIs it correct information? (Y/N)");
                                System.out.println(msg);
                                String ans = br.readLine().trim();
                                if(ans.equalsIgnoreCase("y")) {
                                    inputIsDone = true;
                                    break;
                                } else if(ans.equalsIgnoreCase("n")) {
                                    System.out.println("\nType again...");
                                    msg = "";
                                    break;
                                } else {
                                    System.out.println("\nTyped wrong answer");
                                }
                            }
                        }

                        System.out.println("\n ** Sending an event(ID:" + EventId.ApplicationCheck + ") with ");
                        System.out.println("\n ** Message \"" + msg + "\" ...");
                        clientInputComponent.sendClientInput(EventId.ApplicationCheck, msg);
                    } else if(selection.equals("0")) {
                    	System.out.println("\nSending an event(ID:" + EventId.QuitTheSystem + ")...");
                        clientInputComponent.sendClientInput(EventId.QuitTheSystem, null);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }                
            }
        }
    }

}
