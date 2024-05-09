package com.AbdoHalim.LMS.Controller;


import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.CourseMaterials;
import com.AbdoHalim.LMS.Model.CourseMaterialsModel;
import com.AbdoHalim.LMS.Model.AddGradeModel;
import com.AbdoHalim.LMS.Model.YearWorkModel;
import com.AbdoHalim.LMS.Repository.CourseRepo;
import com.AbdoHalim.LMS.Service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final CourseRepo courseRepo;
    @GetMapping("/ViewMyCourses")
    public ResponseEntity<List<Course>> viewCourses(){
        return doctorService.retrieveMyCourses();
    }
    @GetMapping("/viewCourseMaterial/{course_id}")
    public ResponseEntity<List<CourseMaterials>> retrieveCourseMaterials(@PathVariable Long course_id){
        return doctorService.retrieveCourseMaterials(course_id);
    }
    @PostMapping("/Course/{course_id}/AddMaterial")
    public ResponseEntity<String> addCourseMaterial(@PathVariable Long course_id,
                                                    @RequestBody CourseMaterialsModel courseMaterialsModel){
        return doctorService.addCourseMaterial(course_id,courseMaterialsModel);
    }
    @PutMapping("/Course/{course_id}/ModifyMaterials/{material_id}")
    public ResponseEntity<String>updateCourseMaterials(@PathVariable Long course_id,
                                                       @PathVariable Long material_id,
                                                       @RequestBody CourseMaterialsModel courseMaterialsModel){
        return doctorService.updateCourseMaterials(course_id,material_id,courseMaterialsModel);
    }
    @PostMapping("/Course/{courseId}/AllYearWork/")
    public ResponseEntity<String> addYearWork(@PathVariable Long courseId
            , @RequestBody YearWorkModel yearWorkModel){
        return doctorService.addYearWork(courseId,yearWorkModel);
    }
    @PostMapping("Course/{course_id}/MidTermGrade")
    public ResponseEntity<String> addMidTermGrade(@PathVariable Long course_id
            ,@RequestBody AddGradeModel addGradeModel){
        Optional<Course> course=courseRepo.findById(course_id);
        if (!course.isPresent()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",course_id));
        }
        return doctorService.addGrade( course_id,  addGradeModel,course.get().getMidterm(),"MidTerm");
    }
    @PostMapping("Course/{course_id}/QuizGrade")
    public ResponseEntity<String> addQuizGrade(@PathVariable Long course_id
            ,@RequestBody AddGradeModel addGradeModel){
        Optional<Course> course=courseRepo.findById(course_id);
        if (!course.isPresent()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",course_id));
        }
        return doctorService.addGrade( course_id,  addGradeModel,course.get().getQuiz(),"Quiz");
    }
    @PostMapping("Course/{course_id}/AssignmentGrade")
    public ResponseEntity<String> addAssignmentGrade(@PathVariable Long course_id
            ,@RequestBody AddGradeModel addGradeModel){
        Optional<Course> course=courseRepo.findById(course_id);
        if (!course.isPresent()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",course_id));
        }
        return doctorService.addGrade( course_id,  addGradeModel,course.get().getAssigment(),"Assignment");
    }
    @PostMapping("Course/{course_id}/PracticalGrade")
    public ResponseEntity<String> addPracticalGrade(@PathVariable Long course_id
            ,@RequestBody AddGradeModel addGradeModel){
        Optional<Course> course=courseRepo.findById(course_id);
        if (!course.isPresent()){
            return ResponseEntity.badRequest().body(String.format("Course with id : %d note exist",course_id));
        }
        return doctorService.addGrade( course_id,  addGradeModel,course.get().getPractical(),"Practical");
    }
    @PostMapping("Course/{course_id}/FinalExamGrade")
    public ResponseEntity<String> addFinalExamGrade(@PathVariable Long course_id
            ,@RequestBody AddGradeModel addGradeModel){
        return doctorService.addFinalExamGrade(course_id,addGradeModel);
    }



}
