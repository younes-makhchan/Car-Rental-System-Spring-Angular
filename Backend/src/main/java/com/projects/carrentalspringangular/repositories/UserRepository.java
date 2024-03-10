package com.projects.carrentalspringangular.repositories;

import com.projects.carrentalspringangular.entities.User;
import com.projects.carrentalspringangular.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsUserByEmail(String email); //valid  but we can do better
    User findByUserRole(UserRole role);
    // optional give us the advantage  or having orElse  rathern then null directly
    Optional<User> findFirstByEmail(String email);//findby give exception for many but findFirstby don't
}
