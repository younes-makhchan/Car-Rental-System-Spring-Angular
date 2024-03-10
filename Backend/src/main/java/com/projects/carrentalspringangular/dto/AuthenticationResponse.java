package com.projects.carrentalspringangular.dto;

import com.projects.carrentalspringangular.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private  String jwt;
    private UserRole userRole;
    private  Long userId;

}
