package com.AbdoHalim.LMS.Service;

import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.CourseMaterials;
import com.AbdoHalim.LMS.Entity.Student;
import com.AbdoHalim.LMS.Entity.StudentCourse;
import com.AbdoHalim.LMS.Model.YearWorkModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    Student currantStudent();
    ResponseEntity<String> coursesRegistration(List<Long> coursesIds);

    ResponseEntity<List<Course>> retriveMyCourses();

    ResponseEntity<List<StudentCourse>> retriveHistoryCourses();

    ResponseEntity<Course> retriveCourseDetails(Long id);

    ResponseEntity<List<CourseMaterials>> retriveCourseMaterials(Long id);


    ResponseEntity<List<StudentCourse>> retriveCourseGrades();

    List<Course> registeredCourses();
}
