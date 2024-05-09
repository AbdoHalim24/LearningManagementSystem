package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.Course;
import com.AbdoHalim.LMS.Entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepo extends JpaRepository<CourseRegistration,Long> {

    @Query(value ="SELECT * FROM course_registration where student_id=?1" ,nativeQuery = true)
    CourseRegistration findByStudentId(Long studentId);

    @Query(value = "SELECT c.* FROM Course c " +
            "JOIN RegistrationCourse rc ON c.id = rc.course_id " +
            "JOIN CourseRegistration cr ON rc.registration_id = cr.id " +
            "WHERE cr.student_id = :studentId", nativeQuery = true)
    List<Course> findCoursesByStudentId(Long studentId);


}
