package com.projects.carrentalspringangular.controllers;

import com.projects.carrentalspringangular.dto.AuthenticationRequest;
import com.projects.carrentalspringangular.dto.AuthenticationResponse;
import com.projects.carrentalspringangular.dto.SignupRequest;
import com.projects.carrentalspringangular.dto.UserDto;
import com.projects.carrentalspringangular.entities.User;
import com.projects.carrentalspringangular.repositories.UserRepository;
import com.projects.carrentalspringangular.services.auth.AuthService;
import com.projects.carrentalspringangular.services.jwt.UserService;
import com.projects.carrentalspringangular.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth") // meaning the auth controller auto start from /api/auth
@RequiredArgsConstructor
public class AuthController {
    private  final AuthService authService;
    private final AuthenticationManager  authenticationManager;
    private  final JWTUtil  jwtUtil;
    private  final UserService userService;
    private  final UserRepository userRepository;
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
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws  BadCredentialsException, DisabledException, UsernameNotFoundException {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword());
        try{
            Authentication authenticate = authenticationManager.authenticate(authToken);

        }catch (BadCredentialsException  e){
            throw new BadCredentialsException("Incorrect Username or password");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(token);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return  authenticationResponse;

    }
}
