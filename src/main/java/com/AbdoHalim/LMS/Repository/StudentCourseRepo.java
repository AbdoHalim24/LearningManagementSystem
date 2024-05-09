package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.StudentCourse;
import com.AbdoHalim.LMS.Model.YearWorkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepo  extends JpaRepository<StudentCourse,Long> {

    @Query(value = "SELECT * FROM student_course WHERE student_id = ?1;",nativeQuery = true)
    List<StudentCourse> findByStudentID(Long id);

@Query(value ="SELECT * FROM student_course where student_id=?1 and course_id=?2 " ,nativeQuery = true)
    StudentCourse findByStudentIdAndCourseId(Long studentId, Long courseId);
}
