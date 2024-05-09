package com.AbdoHalim.LMS.Service.Implementation;

import com.AbdoHalim.LMS.Entity.*;
import com.AbdoHalim.LMS.Model.YearWorkModel;
import com.AbdoHalim.LMS.Repository.CourseRegistrationRepo;
import com.AbdoHalim.LMS.Repository.CourseRepo;
import com.AbdoHalim.LMS.Repository.StudentCourseRepo;
import com.AbdoHalim.LMS.Repository.StudentRepo;
import com.AbdoHalim.LMS.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {
    private final CourseRepo courseRepo;
    private final CourseRegistrationRepo courseRegistrationRepo;
    private final StudentRepo studentRepo;
    private final StudentCourseRepo studentCourseRepo;

    @Override
    public Student currantStudent() {
        return null;
    }

    @Override
    public ResponseEntity<String> coursesRegistration(List<Long> coursesIds) {
        Student student=currantStudent();
        //make Validation of the entered data
        for (Long id: coursesIds){
            Optional<Course> course= courseRepo.findById(id);
            if (!course.isPresent())
                return ResponseEntity.badRequest()
                        .body(String.format("Course with id : %d is Not exist. ",id));
            if (course.get().getLevel()> student.getLevel())
                return ResponseEntity.badRequest()
                        .body(String.format("Course with id : %d not allowed for Your Level.",id));
            if (!course.get().getDepartment().equals(student.getDepartment())
                    &&!course.get().getDepartment().equals(Department.GENERAL))
                return ResponseEntity.badRequest()
                        .body(String.format("Course with id : %d is Not in Your Department",id));
        }
        List<Course>courseList=new ArrayList<>();
        for (Long id:coursesIds){
            Optional<Course> course=courseRepo.findById(id);
                courseList.add(course.get());
        }
        CourseRegistration courseRegistration=new CourseRegistration();
        courseRegistration.setCourseList(courseList);
        courseRegistration.setStudent(currantStudent());
        courseRegistrationRepo.save(courseRegistration);
        return ResponseEntity.ok("Courses Registration Successfully Waite the AcademicAdvisor to Accept");
    }
    @Override
    public ResponseEntity<List<Course>> retriveMyCourses() {
        Student student=currantStudent();
       List<Course>courseList=new ArrayList<>();
       List<StudentCourse> studentCourseList=studentCourseRepo.findByStudentID(student.getStudentId());
        for (StudentCourse studentCourse:studentCourseList){
            courseList.add(studentCourse.getCourse());
        }
       return ResponseEntity.ok(courseList);
    }

    @Override
    public ResponseEntity<List<StudentCourse>> retriveHistoryCourses() {
        Student student=currantStudent();
        return ResponseEntity.ok(student.getHistoryCourses());
    }

    @Override
    public ResponseEntity<Course> retriveCourseDetails(Long id) {
        Optional<Course> course=courseRepo.findById(id);
        if (!course.isPresent()){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(course.get());
    }

    @Override
    public ResponseEntity<List<CourseMaterials>> retriveCourseMaterials(Long id) {
        Optional<Course> course=courseRepo.findById(id);
        if (!course.isPresent()){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(course.get().getCourseMaterials());
    }

    @Override
    public ResponseEntity<List<StudentCourse>> retriveCourseGrades() {
        Student student=currantStudent();
        List<StudentCourse> courseGradesList=studentCourseRepo.findByStudentID(student.getStudentId());
        return ResponseEntity.ok(courseGradesList);
    }

    @Override
    public List<Course> registeredCourses() {
        CourseRegistration courseRegistration= courseRegistrationRepo.
                findByStudentId(currantStudent().getStudentId());

        return courseRegistration.getCourseList();
    }


}
