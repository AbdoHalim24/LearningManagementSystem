package com.AbdoHalim.LMS.Repository;

import com.AbdoHalim.LMS.Entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepo extends JpaRepository<Administrator,Long> {
}
