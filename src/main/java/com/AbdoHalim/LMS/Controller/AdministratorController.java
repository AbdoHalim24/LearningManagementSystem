package com.AbdoHalim.LMS.Controller;

import com.AbdoHalim.LMS.Entity.AcademicAdvisor;
import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.Doctor;
import com.AbdoHalim.LMS.Model.AssignAcademicAdvisorModel;
import com.AbdoHalim.LMS.Model.CourseModel;
import com.AbdoHalim.LMS.Model.UserModel;
import com.AbdoHalim.LMS.Service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/Administrator")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;
    @PostMapping("/AddDoctor")
    public ResponseEntity<String> SavaDoctor(@RequestBody UserModel userModel){
        return administratorService.addDoctor(userModel);
    }
    @GetMapping("/DeleteDoctor/{id}")
    public ResponseEntity<String> DeleteDoctor(@PathVariable Long id){
        return administratorService.deleteDoctor(id);
    }

    @PostMapping("/AddAcademicAdvisor")
    public ResponseEntity<String> SavaAcademicAdvisor(@RequestBody UserModel userModel){
        return administratorService.addAcademicAdvisor(userModel);
    }
    @GetMapping("/RemoveAcademicAdvisor/{id}")
    public ResponseEntity<String> DeleteAcademicAdvisor(@PathVariable Long id){
        return administratorService.deleteAcademicAdvisor(id);
    }
    @PostMapping("/AddStudent")
    public ResponseEntity<String> SavaStudent(@RequestBody UserModel userModel){
        return administratorService.addStudent(userModel);
    }
    @GetMapping("/RemoveStudent/{id}")
    public ResponseEntity<String> DeleteStudent(@PathVariable Long id ){
        return administratorService.deleteStudent(id);
    }
    @PostMapping("/AddCourse/")
    public ResponseEntity<String> AddNewCourse(@RequestBody CourseModel courseModel){
        return administratorService.addCourse(courseModel);
    }
    @PostMapping("/DeleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return administratorService.deleteCourse(id);
    }
    @GetMapping("/course/{course_id}/doctor/{doctor_email}")
    public ResponseEntity<String> AddDoctorToCourse(@PathVariable Long course_id
            ,@PathVariable String doctor_email ){
        return administratorService.addDoctorToCourse(course_id,doctor_email);
    }
    @GetMapping("/ViewDoctorCourse/{id}")
    private ResponseEntity<List<Course>>retrieveDoctorCourses(@PathVariable Long id){
        return administratorService.retrieveDoctorCourses(id);
    }
    @PostMapping("/AssignAcademicAdvisor")
    private ResponseEntity<String >AssignAcademicAdvisor(@RequestBody AssignAcademicAdvisorModel assignAcademicAdvisorModel){
        return administratorService.assignAcademicAdvisor(assignAcademicAdvisorModel);
    }
    @GetMapping("/RetrieveDoctors")
    public ResponseEntity<List<Doctor>> retrieveAllDoctors(){
        return administratorService.retrieveAllDoctors();
    }
    @GetMapping("/RetrieveAcademicAdvisor")
    public ResponseEntity<List<AcademicAdvisor>> retrieveAllAcademicAdvisor(){
        return administratorService.retrieveAllAcademicAdvisor();
    }
    @GetMapping("/AddDepartment/{depart}/Student/{student_id}")
    public ResponseEntity<String> AddStudentToDepartment(@PathVariable String depart
            ,@PathVariable Long student_id){
        return administratorService .addStudentToDepartment(student_id,depart);
    }



}
