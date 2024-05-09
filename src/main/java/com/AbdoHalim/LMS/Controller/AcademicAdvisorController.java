package com.AbdoHalim.LMS.Controller;

import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.Student;
import com.AbdoHalim.LMS.Entity.StudentCourse;
import com.AbdoHalim.LMS.Service.AcademicAdvisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("AcademicAdvisor")
@RequiredArgsConstructor
public class AcademicAdvisorController {

    private final AcademicAdvisorService academicAdvisorService;

    @GetMapping("/StudentsRequests")
    public ResponseEntity<List<Student>> retrieveStudentsRequests(){
        return academicAdvisorService.StudentsRequests();
    }
    @GetMapping("/RegistrationRequests/{student_id}")
    public ResponseEntity<List<Course>> retrieveCoursesRegistrationRequests(@PathVariable Long student_id) {
        return academicAdvisorService.StudentCoursesRegistration(student_id);
    }
    @GetMapping("/HistoryCourses/{student_id}")
    public ResponseEntity<List<StudentCourse>> retrieveStudentHistoryCourses(@PathVariable long student_id){
        return academicAdvisorService.retrieveStudentHistoryCourses(student_id);
    }
    @PostMapping("/Student/{student_id}/AddCourses")
    public ResponseEntity<String> addCoursesToStudent(@PathVariable Long student_id,@RequestBody List<Long>courses_ids){
        return academicAdvisorService.addCoursesToStudent(student_id,courses_ids);
    }

}
