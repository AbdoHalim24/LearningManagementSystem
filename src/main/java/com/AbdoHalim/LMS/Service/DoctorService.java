package com.AbdoHalim.LMS.Service;

import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.CourseMaterials;
import com.AbdoHalim.LMS.Entity.Doctor;
import com.AbdoHalim.LMS.Model.CourseMaterialsModel;
import com.AbdoHalim.LMS.Model.AddGradeModel;
import com.AbdoHalim.LMS.Model.YearWorkModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DoctorService {
    Doctor currantDoctor();
    ResponseEntity<List<Course>> retrieveMyCourses();

    ResponseEntity<List<CourseMaterials>> retrieveCourseMaterials(Long courseId);

    ResponseEntity<String> addCourseMaterial(Long courseId, CourseMaterialsModel courseMaterialsModel);

    ResponseEntity<String> updateCourseMaterials(Long courseId, Long materialId, CourseMaterialsModel courseMaterialsModel);

    ResponseEntity<String> addYearWork(Long courseId, YearWorkModel yearWorkModel);

    ResponseEntity<String> addGrade(Long courseId, AddGradeModel addGradeModel, int i, String GradeName);
    ResponseEntity<String> addFinalExamGrade(Long courseId, AddGradeModel addGradeModel);
}
