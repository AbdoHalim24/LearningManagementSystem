package com.AbdoHalim.LMS.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User{
    @Id
    private Long studentId;

    private int level;
    private String phone;
    private Long gpa;
    private int totalHours;
    @Enumerated
    private Department department;
    @ManyToOne
    @JoinColumn(name = "advisor_id",referencedColumnName = "id")
    private AcademicAdvisor academicAdvisor;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<StudentCourse>historyCourses=new ArrayList<>();
}
