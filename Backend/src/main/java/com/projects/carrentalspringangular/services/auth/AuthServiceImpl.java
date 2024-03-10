package com.projects.carrentalspringangular.services.auth;

import com.projects.carrentalspringangular.dto.SignupRequest;
import com.projects.carrentalspringangular.dto.UserDto;
import com.projects.carrentalspringangular.entities.User;
import com.projects.carrentalspringangular.enums.UserRole;
import com.projects.carrentalspringangular.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //generate construct for final and @NonNull
public class AuthServiceImpl implements AuthService {
    private  final UserRepository userRepository;

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User user = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(signupRequest.getPassword()))
                .userRole(UserRole.CUSTOMER)//you can use if statment to control it with a string
                .build();
        User createdUser=userRepository.save(user);
        // i think better to return a success
        UserDto userDto =new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getUserRole());

        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
