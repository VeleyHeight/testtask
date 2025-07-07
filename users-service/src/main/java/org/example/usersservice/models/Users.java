package org.example.usersservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true, nullable=false, length=50)
    private String username;
    @Column(nullable=false, length=50)
    private String password;
    @Column(nullable=false, length=50, unique=true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Roles roles;
}
