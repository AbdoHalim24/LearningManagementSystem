package com.AbdoHalim.LMS.Service;

import com.AbdoHalim.LMS.Entity.AcademicAdvisor;
import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.Doctor;
import com.AbdoHalim.LMS.Model.AdministratorModel;
import com.AbdoHalim.LMS.Model.AssignAcademicAdvisorModel;
import com.AbdoHalim.LMS.Model.CourseModel;
import com.AbdoHalim.LMS.Model.UserModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdministratorService {
    ResponseEntity<String> addDoctor(UserModel userModel);

    ResponseEntity<String> addStudent(UserModel userModel);

    ResponseEntity<String> addAcademicAdvisor(UserModel userModel);

    ResponseEntity<String> addCourse(CourseModel courseModel);

    ResponseEntity<String> addDoctorToCourse(Long courseid, String doctoremail);

    ResponseEntity<List<Course>> retrieveDoctorCourses(Long id);

    ResponseEntity<String> deleteAcademicAdvisor(Long id);

    ResponseEntity<String> deleteStudent(Long id);

    ResponseEntity<String> deleteDoctor(Long id);

    ResponseEntity<String> deleteCourse(Long id);

    ResponseEntity<String> assignAcademicAdvisor(AssignAcademicAdvisorModel assignAcademicAdvisorModel);

    ResponseEntity<List<Doctor>> retrieveAllDoctors();

    ResponseEntity<List<AcademicAdvisor>> retrieveAllAcademicAdvisor();

    ResponseEntity<String> addStudentToDepartment(Long studentId, String depart);

    ResponseEntity<String> addAdministrator(AdministratorModel userModel);
}
