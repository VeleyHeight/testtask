package org.example.usersservice.usersService;

import org.example.usersservice.usersDTO.JwtAuthenticationDTO;
import org.example.usersservice.usersDTO.UsersDTO;
import org.example.usersservice.usersDTO.UsersLoginDTO;

public interface UsersService {
    UsersDTO save(UsersDTO usersDTO);
    JwtAuthenticationDTO createToken(UsersLoginDTO usersLoginDTO);
    JwtAuthenticationDTO updateToken(String token) throws Exception;
}
