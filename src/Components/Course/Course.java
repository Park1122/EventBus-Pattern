package Components.Course;
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
public class Course {

    protected String courseId;
    protected String instructor;
    protected String name;
    protected ArrayList prerequisiteCoursesList;

    /**
     * Constructor. 
     * @param inputString
     */
    public Course(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);

        this.courseId = stringTokenizer.nextToken();
        this.instructor = stringTokenizer.nextToken();
        this.name = stringTokenizer.nextToken();
        
        this.prerequisiteCoursesList = new ArrayList();
        while(stringTokenizer.hasMoreTokens()) {
            this.prerequisiteCoursesList.add(stringTokenizer.nextToken());
        }
    }

    /**
     * @param courseId
     * @return
     */
    public boolean match(String courseId) {
        return this.courseId.equals(courseId);
    }
    
    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /* 
     */
    public String toString() {
        String stringReturn = this.courseId + " " + this.instructor + " " + this.name;
        
        for(int i = 0; i < this.prerequisiteCoursesList.size(); i++) {
            stringReturn += " " + this.prerequisiteCoursesList.get(i).toString();
        }
        
        return stringReturn;
    }
}
