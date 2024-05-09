package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {

    @Query(value = "select * from course where doctor_id=?1",nativeQuery = true)
    List<Course> findAllByDoctorId(Long id);
    @Query(value = "select * from course where name=?1",nativeQuery = true)

    Course findByName(String name);
}
