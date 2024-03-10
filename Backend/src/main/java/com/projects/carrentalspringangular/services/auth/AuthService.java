package com.projects.carrentalspringangular.services.auth;

import com.projects.carrentalspringangular.dto.SignupRequest;
import com.projects.carrentalspringangular.dto.UserDto;
import com.projects.carrentalspringangular.entities.User;

import java.util.List;

public interface AuthService {
    UserDto createCustomer(SignupRequest signupRequest);
    public  void createAdminAccount();
    boolean hasCustomerWithEmail(String email);
}
