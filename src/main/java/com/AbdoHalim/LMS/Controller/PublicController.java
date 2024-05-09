package com.AbdoHalim.LMS.Controller;

import com.AbdoHalim.LMS.Entity.Administrator;
import com.AbdoHalim.LMS.Model.AdministratorModel;
import com.AbdoHalim.LMS.Model.UserModel;
import com.AbdoHalim.LMS.Service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Public")
@RequiredArgsConstructor
public class PublicController {
    private final AdministratorService administratorService;

    @PostMapping("/AdministratorRegister")
    public ResponseEntity<String>AddAdministrator(@RequestBody AdministratorModel administratorModel){
        return administratorService.addAdministrator(administratorModel);
    }

}
