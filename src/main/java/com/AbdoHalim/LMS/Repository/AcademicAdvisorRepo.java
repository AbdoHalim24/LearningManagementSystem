package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.AcademicAdvisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicAdvisorRepo  extends JpaRepository<AcademicAdvisor,Long> {
    AcademicAdvisor findByEmail(String email);
}
