package org.example.usersservice.usersService;

import org.example.usersservice.usersDTO.JwtAuthenticationDTO;
import org.example.usersservice.usersDTO.UsersDTO;

public interface UsersService {
    UsersDTO save(UsersDTO usersDTO);
    JwtAuthenticationDTO createToken(UsersDTO usersDTO);
    JwtAuthenticationDTO updateToken(String token) throws Exception;
}
