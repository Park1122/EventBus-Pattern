package Components.Student;
/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class Student {

	protected String studentId;
    protected String name;
    protected String department;
    protected ArrayList completedCoursesList;
    protected int studentBalance;

    /**
     * Constructor. 
     * @param inputString
     */
    public Student(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.studentId = stringTokenizer.nextToken();
    	this.name = stringTokenizer.nextToken();
    	this.name = this.name + " " + stringTokenizer.nextToken();
    	this.department = stringTokenizer.nextToken();

    	this.completedCoursesList = new ArrayList();
    	while (stringTokenizer.hasMoreTokens()) {
    		this.completedCoursesList.add(stringTokenizer.nextToken());
    	}
    }

    /**
     * @param studentId
     * @return
     */
    public boolean match(String studentId) {
        return this.studentId.equals(studentId);
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return
     */
    public ArrayList getCompletedCourses() {
        return this.completedCoursesList;
    }

    public String toString() {
        String stringReturn = this.studentId + " " + this.name + " " + this.department;

        for (int i = 0; i < this.completedCoursesList.size(); i++) {
            stringReturn = stringReturn + " " + this.completedCoursesList.get(i).toString();
        }

        return stringReturn;
    }

    /**
     * @return
     */
    public int getBalance() {
		return this.studentBalance;
	}

	public void decrementBalance() {
		this.studentBalance--;
	}

}
