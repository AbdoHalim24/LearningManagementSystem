package com.AbdoHalim.LMS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGradeModel {
    private Long StudentId;
    private float Grade;

}
