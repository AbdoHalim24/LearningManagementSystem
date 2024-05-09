package com.AbdoHalim.LMS.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CourseMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lecturepdf;
    private String lecturelink;
    private String labpdf;
    private String lablink;
    @ManyToOne(cascade =  CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private  Course course;
}
