package Components.Course;
/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class CourseComponent {
    protected ArrayList vCourse;
    
    /**
     * Constructor. 
     * 
     * @param sCourseFileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public CourseComponent(String sCourseFileName) throws FileNotFoundException, IOException {
        BufferedReader objCourseFile  = new BufferedReader(new FileReader(sCourseFileName));
        
        this.vCourse  = new ArrayList();
        
        while (objCourseFile.ready()) {
            String courseInfo = objCourseFile.readLine();
            if(!courseInfo.equals("")) {
                this.vCourse.add(new Course(courseInfo));
            }
        }
        
        objCourseFile.close();
    }
    
    /**
     * @return
     */
    public ArrayList getAllCourseRecords() {
        return this.vCourse;
    }
    
    /**
     * 
     * @param courseId
     * @return
     */
    public boolean isRegisteredCourse(String courseId) {
        for (int i = 0; i < this.vCourse.size(); i++) {
            Course course = (Course) this.vCourse.get(i);
            if(course.match(courseId)) {
                return true;
            }
        }
        return false;
    }
}
