package com.AbdoHalim.LMS.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int level;
    @Enumerated
    private Department department;
    private int quiz;
    private int finalexam;
    private int practical;
    private int hours;
    private int midterm;
    private int assigment;
    private double Grade;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseMaterials> courseMaterials=new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    private Doctor doctor;
}
