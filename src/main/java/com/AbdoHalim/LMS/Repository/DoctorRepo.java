package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    Doctor findByEmail(String email);
}
