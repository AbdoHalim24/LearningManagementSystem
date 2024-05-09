package com.AbdoHalim.LMS.Controller;

import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.CourseMaterials;
import com.AbdoHalim.LMS.Entity.StudentCourse;
import com.AbdoHalim.LMS.Model.YearWorkModel;
import com.AbdoHalim.LMS.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/CoursesRegistration")
    public ResponseEntity<String> CoursesRegistration(@RequestBody List<Long> courses_ids) {
        return studentService.coursesRegistration(courses_ids);
    }
    @GetMapping("/ShowMyCourses")
    public ResponseEntity<List<Course>> showMyCourses(){
        return studentService.retriveMyCourses();
    }
    @GetMapping("/CourseDetails/{id}")
    public ResponseEntity<Course> CourseDetails(@PathVariable Long id){
        return studentService.retriveCourseDetails(id);
    }
    @GetMapping("/CourseMaterials/{id}")
    public ResponseEntity<List<CourseMaterials>> CourseMaterials(@PathVariable Long id){
        return studentService.retriveCourseMaterials(id);
    }
    //used for get all year work grades and final Grades
    @GetMapping("/RetriveCourseGrades")
    public ResponseEntity<List<StudentCourse>> retriveCourseGrades(){
        return studentService.retriveCourseGrades();
    }
    @GetMapping("/RegisteredCourses")
    public List<Course> registeredCourses(){
        return studentService.registeredCourses();
    }





}
