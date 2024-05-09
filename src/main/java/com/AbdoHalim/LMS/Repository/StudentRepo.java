package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
    @Query(value = "select max(studnetid) from student", nativeQuery = true)
    Long getMaxStudentId();

    @Query(value = "update student set advisor_id=?1 where level =?2 and department=?3", nativeQuery = true)
    void addAcademicAdvisor(Long id, Integer level, String department);

    @Query(value = "SELECT * from student where advisor_id=?1", nativeQuery = true)
    List<Student> finalAllStudentByAcademicAdvisorId(Long id);

    @Query(value = "select * from student where student_id =?1", nativeQuery = true)
    Student findByStudentId(Long studentId);
}