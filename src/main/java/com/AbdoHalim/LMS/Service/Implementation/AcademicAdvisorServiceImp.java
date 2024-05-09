package com.AbdoHalim.LMS.Service.Implementation;

import com.AbdoHalim.LMS.Entity.*;
import com.AbdoHalim.LMS.Repository.*;
import com.AbdoHalim.LMS.Service.AcademicAdvisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class AcademicAdvisorServiceImp implements AcademicAdvisorService {
    private final StudentRepo studentRepo;
    private final CourseRegistrationRepo courseRegistrationRepo;
    private final StudentCourseRepo studentCourseRepo;
    private final CourseRepo courseRepo;

    @Override
    public com.AbdoHalim.LMS.Entity.AcademicAdvisor currantAcademicAdvisor() {
        return null;
    }

    @Override
    public ResponseEntity<List<Student>> StudentsRequests() {
        AcademicAdvisor academicAdvisor=currantAcademicAdvisor();
        List<Student>studentList=studentRepo.finalAllStudentByAcademicAdvisorId(academicAdvisor.getId());
        return ResponseEntity.ok(studentList) ;
    }

    @Override
    public ResponseEntity<List<Course>> StudentCoursesRegistration(Long studentId) {
        Student student=studentRepo.findByStudentId(studentId);
        if (student==null)
            return ResponseEntity.badRequest().body(null);

        List<Course> courseList= courseRegistrationRepo.findCoursesByStudentId(studentId);
        return ResponseEntity.ok(courseList);
    }

    @Override
    public ResponseEntity<List<StudentCourse>> retrieveStudentHistoryCourses(long studentId) {
        Student student=studentRepo.findByStudentId(studentId);
        if (student==null)
            return ResponseEntity.badRequest().body(null);

        return ResponseEntity.ok(student.getHistoryCourses());
    }

    @Override
    public ResponseEntity<String> addCoursesToStudent(Long studentId, List<Long> coursesIds) {
        Student student=studentRepo.findByStudentId(studentId);
        //make Validation of the entered data
        if (student==null)
            return ResponseEntity.badRequest().body(String.format("Student with id : %d is not exist",studentId));
        for (Long id: coursesIds){
            Optional<Course> course= courseRepo.findById(id);
            if (course.isEmpty())
                return ResponseEntity.badRequest()
                        .body(String.format("Course with id : %d is Not exist. ",id));
            if (course.get().getLevel()> student.getLevel())
                return ResponseEntity.badRequest()
                        .body(String.format("Course with id : %d not allowed for Student Level.",id));
            if (!course.get().getDepartment().equals(student.getDepartment())
                    &&!course.get().getDepartment().equals(Department.GENERAL))
                return ResponseEntity.badRequest()
                        .body(String.format("Course with id : %d is Not in Student Department",id));
        }
        StudentCourse studentCourse= new StudentCourse();
        for (Long id: coursesIds) {
            Optional<Course> course = courseRepo.findById(id);
             studentCourse.setStudent(student);
             studentCourse.setCourse(course.get());
             studentCourseRepo.save(studentCourse);
        }
        return ResponseEntity.ok("Courses Added Successfully");
    }



}
