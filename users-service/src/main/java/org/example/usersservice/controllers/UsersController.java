package org.example.usersservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.usersservice.usersDTO.JwtAuthenticationDTO;
import org.example.usersservice.usersDTO.UsersDTO;
import org.example.usersservice.usersDTO.UsersLoginDTO;
import org.example.usersservice.usersService.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService service;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationDTO> login(@Valid @RequestBody UsersLoginDTO usersLoginDTO) {
        return ResponseEntity.ok().body(service.createToken(usersLoginDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<UsersDTO> register(@Valid @RequestBody UsersDTO usersDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usersDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationDTO> refresh(@Valid @RequestBody String refreshToken) throws Exception {
        return ResponseEntity.ok(service.updateToken(refreshToken));
    }
}
