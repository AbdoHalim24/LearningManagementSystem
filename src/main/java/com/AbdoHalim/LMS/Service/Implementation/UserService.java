package com.AbdoHalim.LMS.Service.Implementation;

import com.AbdoHalim.LMS.Entity.Department;
import com.AbdoHalim.LMS.Entity.Student;
import org.springframework.stereotype.Service;

@Service
public class UserService implements com.AbdoHalim.LMS.Service.UserService {


    @Override
    public boolean isDepartmentExist(String depart) {
        boolean departExist=false;
        for (Department department:Department.values()){
            if (department.name().equalsIgnoreCase(depart)){
                departExist=true;
                break;
            }
        }
        return departExist;
    }
}
