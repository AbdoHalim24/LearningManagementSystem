package com.AbdoHalim.LMS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradesModel {
    private String courseName;
    private int hours;
    private String grade ;
    private double points;

}
