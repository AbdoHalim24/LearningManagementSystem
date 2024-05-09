package com.AbdoHalim.LMS.Service.Implementation;

import com.AbdoHalim.LMS.Entity.*;
import com.AbdoHalim.LMS.Model.CourseMaterialsModel;
import com.AbdoHalim.LMS.Model.AddGradeModel;
import com.AbdoHalim.LMS.Model.GradPointsModel;
import com.AbdoHalim.LMS.Model.YearWorkModel;
import com.AbdoHalim.LMS.Repository.CourseMaterialsRepo;
import com.AbdoHalim.LMS.Repository.CourseRepo;
import com.AbdoHalim.LMS.Repository.StudentCourseRepo;
import com.AbdoHalim.LMS.Repository.StudentRepo;
import com.AbdoHalim.LMS.Service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImp implements DoctorService {
    private final CourseRepo courseRepo;
    private final CourseMaterialsRepo courseMaterialsRepo;
    private final StudentRepo studentRepo;
    private final StudentCourseRepo studentCourseRepo;
    @Override
    public Doctor currantDoctor() {
        return null;
    }
    @Override
    public ResponseEntity<List<Course>> retrieveMyCourses() {
        List<Course> courseList= courseRepo.findAllByDoctorId(currantDoctor().getId());
        return ResponseEntity.ok(courseList);

    }

    @Override
    public ResponseEntity<List<CourseMaterials>> retrieveCourseMaterials(Long courseId) {
        Optional<Course> course=courseRepo.findById(courseId);
        if (course.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(course.get().getCourseMaterials());
    }

    //Uses for modify and add new Course Material
    @Override
    public ResponseEntity<String> addCourseMaterial(Long courseId, CourseMaterialsModel courseMaterialsModel) {
        Optional<Course> course=courseRepo.findById(courseId);
        if (course.isEmpty()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",courseId));
        }
        CourseMaterials courseMaterials=new CourseMaterials();
        courseMaterials.setCourse(course.get());
        courseMaterials.setLecturepdf(courseMaterialsModel.getLecturelink());
        courseMaterials.setLecturelink(courseMaterialsModel.getLecturelink());
        courseMaterials.setLabpdf(courseMaterialsModel.getLabpdf());
        courseMaterials.setLablink(courseMaterialsModel.getLablink());
        courseMaterialsRepo.save(courseMaterials);
        return ResponseEntity.ok("Course Materials Added Successfully");
    }

    @Override
    public ResponseEntity<String> updateCourseMaterials(Long courseId, Long materialId, CourseMaterialsModel courseMaterialsModel) {
        Optional<Course> course=courseRepo.findById(courseId);
        if (course.isEmpty()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",courseId));
        }
        Optional<CourseMaterials> courseMaterials=courseMaterialsRepo.findById(materialId);
        if (courseMaterials.isEmpty()){
            return ResponseEntity.badRequest().body(
                    String.format("CourseMaterials with id: %d not exist",materialId));
        }
        courseMaterials.get().setLecturelink(courseMaterialsModel.getLecturelink());
        courseMaterials.get().setLecturepdf(courseMaterialsModel.getLecturepdf());
        courseMaterials.get().setLablink(courseMaterialsModel.getLablink());
        courseMaterials.get().setLabpdf(courseMaterialsModel.getLabpdf());
        courseMaterialsRepo.save(courseMaterials.get());
        return ResponseEntity.ok("Course Materials Updated");
    }

    @Override
    public ResponseEntity<String> addYearWork(Long courseId, YearWorkModel yearWorkModel) {
        Student student=studentRepo.findByStudentId(yearWorkModel.getStudentId());
        //make Validation of the entered data
        if (student==null)
            return ResponseEntity.badRequest().body(String.format("Student with id : %d is not exist",yearWorkModel.getStudentId()));
        Optional<Course> course=courseRepo.findById(courseId);

        if (course.isEmpty()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",courseId));
        }

        if (yearWorkModel.getMidtermGrade()>15 || yearWorkModel.getMidtermGrade()<0
                || yearWorkModel.getAssignmentGrade()>10||yearWorkModel.getAssignmentGrade()<0
                ||yearWorkModel.getQuizGrade()>5 || yearWorkModel.getQuizGrade()<0||
                yearWorkModel.getPracticalGrade()>20|| yearWorkModel.getPracticalGrade()<0){
            return ResponseEntity.badRequest().body("invalid YearWork");
        }
        StudentCourse studentCourse=studentCourseRepo.findByStudentIdAndCourseId(student.getStudentId(),courseId);
        studentCourse.setQuizGrade(yearWorkModel.getQuizGrade());
        studentCourse.setAssignmentGrade(yearWorkModel.getAssignmentGrade());
        studentCourse.setMidtermGrade(yearWorkModel.getMidtermGrade());
        studentCourse.setPracticalGrade(yearWorkModel.getPracticalGrade());
        studentCourseRepo.save(studentCourse);
        return ResponseEntity.ok("Year Work Added Successfully");
    }

    @Override
    public ResponseEntity<String> addFinalExamGrade(Long courseId, AddGradeModel addGradeModel) {
        Student student=studentRepo.findByStudentId(addGradeModel.getStudentId());
        //make Validation of the entered data
        if (student==null)
            return ResponseEntity.badRequest().body(String.format("Student with id : %d is not exist",addGradeModel.getStudentId()));
        Optional<Course> course=courseRepo.findById(courseId);
        if (course.isEmpty()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",courseId));
        }
        if (course.get().getFinalexam()<addGradeModel.getGrade()||course.get().getFinalexam()<0){
            return ResponseEntity.badRequest().body("Invalid Final Exam Grade");
        }
        StudentCourse studentCourse=studentCourseRepo.findByStudentIdAndCourseId(student.getStudentId(),courseId);

        studentCourse.setFinalExamGrade(addGradeModel.getGrade());
        calculateCourseGrade(studentCourse);
        return ResponseEntity.ok("Final Exam Grade Added ");
    }
    @Override
    public ResponseEntity<String> addGrade(Long courseId, AddGradeModel addGradeModel, int CourseGrade, String GradName) {
        // Check if student id is provided
        Student student=studentRepo.findByStudentId(addGradeModel.getStudentId());
        //make Validation on the entered data
        if (student==null)
            return ResponseEntity.badRequest().body(String.format("Student with id : %d is not exist",addGradeModel.getStudentId()));
        // Retrieve the course and student
        Optional<Course> courseOptional = courseRepo.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(String.format("Course with id: %d does not exist", courseId));
        }
        // Validate the grade
        if (addGradeModel.getGrade() > CourseGrade || addGradeModel.getGrade() < 0) {
            return ResponseEntity.badRequest().body("Invalid grade");
        }
        StudentCourse studentCourse = studentCourseRepo.findByStudentIdAndCourseId(student.getStudentId(), courseId);
        switch (GradName) {
            case "MidTerm":
                studentCourse.setMidtermGrade(addGradeModel.getGrade());
                break;
            case "FinalExam":
                studentCourse.setFinalExamGrade(addGradeModel.getGrade());
                break;
            case "Quiz":
                studentCourse.setQuizGrade(addGradeModel.getGrade());
                break;
            case "Assignment":
                studentCourse.setAssignmentGrade(addGradeModel.getGrade());
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid grade type");
        }
        studentCourseRepo.save(studentCourse);
        return ResponseEntity.ok("Grade added successfully");
    }


    private void calculateCourseGrade(StudentCourse studentCourse) {
       double totalCourseGrades=studentCourse.getAssignmentGrade()+studentCourse.getQuizGrade()
               +studentCourse.getPracticalGrade()+studentCourse.getMidtermGrade()
               +studentCourse.getFinalExamGrade();
       studentCourse.setTotalCourseGrades(totalCourseGrades);
        GradPointsModel  gradPointsModel=CalcGradPoints(totalCourseGrades);
        studentCourse.setGrade(gradPointsModel.getGrade());
        studentCourse.setPoints(gradPointsModel.getPoints());
        studentCourseRepo.save(studentCourse);
    }

    private GradPointsModel CalcGradPoints(double totalCourseGrades) {
        GradPointsModel gradPointsModel=new GradPointsModel();
        if (totalCourseGrades>=97){
            gradPointsModel.setPoints(4);
            gradPointsModel.setGrade("A+");
        }
        else if (totalCourseGrades<97&& totalCourseGrades>=94){
            gradPointsModel.setGrade("A");
            gradPointsModel.setPoints(4);
        }
        else if (totalCourseGrades<94&& totalCourseGrades>=90){
            gradPointsModel.setGrade("A-");
            gradPointsModel.setPoints(3.7);
        }
        else if (totalCourseGrades<90&& totalCourseGrades>=87){
            gradPointsModel.setGrade("B+");
            gradPointsModel.setPoints(3.3);
        }
        else if (totalCourseGrades<87&& totalCourseGrades>=84){
            gradPointsModel.setGrade("B");
            gradPointsModel.setPoints(3);
        }
        else if (totalCourseGrades<84&& totalCourseGrades>=80){
            gradPointsModel.setGrade("B-");
            gradPointsModel.setPoints(2.7);
        }
        else if (totalCourseGrades<80&& totalCourseGrades>=77){
            gradPointsModel.setGrade("C+");
            gradPointsModel.setPoints(2.3);
        }
        else if (totalCourseGrades<77&& totalCourseGrades>=74){
            gradPointsModel.setGrade("C");
            gradPointsModel.setPoints(2);
        }
        else if (totalCourseGrades<74&& totalCourseGrades>=70){
            gradPointsModel.setGrade("C-");
            gradPointsModel.setPoints(1.7);
        }
        else if (totalCourseGrades<70&& totalCourseGrades>=67){
            gradPointsModel.setGrade("D+");
            gradPointsModel.setPoints(1.3);
        }
        else if (totalCourseGrades<67&& totalCourseGrades>=64){
            gradPointsModel.setGrade("D");
            gradPointsModel.setPoints(1);
        }
        else if (totalCourseGrades>=60) {
            gradPointsModel.setGrade("D-");
            gradPointsModel.setPoints(0.7);
        }
        else if (totalCourseGrades<60){
            gradPointsModel.setGrade("F");
            gradPointsModel.setPoints(0);
        }
        return gradPointsModel;

    }


}
