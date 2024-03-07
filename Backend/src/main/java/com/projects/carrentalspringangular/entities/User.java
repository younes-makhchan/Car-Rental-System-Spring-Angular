package com.projects.carrentalspringangular.entities;

import com.projects.carrentalspringangular.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
