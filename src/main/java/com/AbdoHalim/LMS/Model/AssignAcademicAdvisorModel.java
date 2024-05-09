package com.AbdoHalim.LMS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignAcademicAdvisorModel {
    private String email;
    private String Department;
    private Integer level;
}
