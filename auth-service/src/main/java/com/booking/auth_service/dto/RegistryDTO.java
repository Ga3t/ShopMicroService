package com.booking.auth_service.dto;


import com.booking.core.enums.RoleEnum;
import lombok.Data;

import java.util.Date;

@Data
public class RegistryDTO {

    private String password;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private RoleEnum role;
    private Date birthday;

}
