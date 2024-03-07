package com.projects.carrentalspringangular.dto;

import com.projects.carrentalspringangular.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private  Long id;// i think this shouldn't exists maybe

    private  String name;

    private String email;


    private UserRole userRole;
}
