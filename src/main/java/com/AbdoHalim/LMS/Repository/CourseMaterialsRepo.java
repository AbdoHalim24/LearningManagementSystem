package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.CourseMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMaterialsRepo extends JpaRepository<CourseMaterials,Long> {
}
