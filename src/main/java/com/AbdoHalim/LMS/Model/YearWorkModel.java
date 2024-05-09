package com.AbdoHalim.LMS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearWorkModel {
    private Long StudentId;
    private float practicalGrade;
    private float quizGrade;
    private float midtermGrade;
    private float assignmentGrade;
}
