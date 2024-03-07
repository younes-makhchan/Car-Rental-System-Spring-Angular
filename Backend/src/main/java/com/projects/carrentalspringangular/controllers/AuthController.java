package com.projects.carrentalspringangular.controllers;

import com.projects.carrentalspringangular.dto.SignupRequest;
import com.projects.carrentalspringangular.dto.UserDto;
import com.projects.carrentalspringangular.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // meaning the auth controller auto start from /api/auth
@RequiredArgsConstructor
public class AuthController {
    private  final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
        if(authService.hasCustomerWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("Email Already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto=authService.createCustomer(signupRequest);
        if(userDto==null) {
            return new ResponseEntity<>("customer not created, try again later", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDto,HttpStatus.CREATED);

    }
}
