package com.AbdoHalim.LMS.Entity;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    @Enumerated
    private Role role;
}
