package com.AbdoHalim.LMS.Service;

import com.AbdoHalim.LMS.Entity.AcademicAdvisor;
import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.Student;
import com.AbdoHalim.LMS.Entity.StudentCourse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AcademicAdvisorService {
    AcademicAdvisor currantAcademicAdvisor();

    ResponseEntity<List<Student>> StudentsRequests();


    ResponseEntity<List<Course>> StudentCoursesRegistration(Long studentId);

    ResponseEntity<List<StudentCourse>> retrieveStudentHistoryCourses(long studentId);

    ResponseEntity<String> addCoursesToStudent(Long studentId, List<Long> coursesIds);

}
