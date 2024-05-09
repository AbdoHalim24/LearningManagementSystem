package com.AbdoHalim.LMS.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private double finalExamGrade;
    private double practicalGrade;
    private double quizGrade;
    private double midtermGrade;
    private double assignmentGrade;
    private double totalCourseGrades;
    private String grade ;
    private double points;

}
