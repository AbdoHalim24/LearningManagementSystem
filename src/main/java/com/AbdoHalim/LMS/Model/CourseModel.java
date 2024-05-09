package com.AbdoHalim.LMS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {
    private String name;
    private String doctoremail;
    private int hours;
    private int finalexam;
    private int level;
    private int  practical;
    private int quiz;
    private int midterm;
    private int assigment;
}
