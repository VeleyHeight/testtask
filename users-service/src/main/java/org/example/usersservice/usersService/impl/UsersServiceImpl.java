package org.example.usersservice.usersService.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.usersservice.mapper.UsersMapper;
import org.example.usersservice.models.Users;
import org.example.usersservice.repository.UsersRepository;
import org.example.usersservice.usersDTO.JwtAuthenticationDTO;
import org.example.usersservice.usersDTO.UsersDTO;
import org.example.usersservice.usersService.UsersService;
import org.example.usersservice.utils.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Override
    @Transactional
    public UsersDTO save(UsersDTO usersDTO) {
        log.info("Saving users: {}", usersDTO);
        if(usersRepository.exsistsByUsername(usersDTO.userName())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User with username  already exists");
        }
        var users = usersMapper.toUsers(usersDTO);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        var savedUsers = usersRepository.save(users);
        log.info("Saved user: {}", savedUsers);
        return usersMapper.toDTO(savedUsers);
    }

    @Override
    @Transactional
    public JwtAuthenticationDTO createToken(UsersDTO usersDTO) {
        UserDetails userDetails = this.loadUserByUsername(usersDTO.userName());
        if (!passwordEncoder.matches(usersDTO.userPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return jwtUtils.generateAccessAndRefreshToken(userDetails);
    }

    @Override
    @Transactional
    public JwtAuthenticationDTO updateToken(String token) throws Exception {
        if(jwtUtils.validateToken(token)) {
            Users users = usersRepository.findByUsername(jwtUtils.extractUsername(token))
                    .orElseThrow(() -> new BadCredentialsException("Invalid token"));
            UserDetails userDetails = this.loadUserByUsername(users.getUsername());
            return jwtUtils.refreshAccessAndRefreshToken(userDetails, token);
        }
        throw new AuthenticationException("Invalid token");
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username).map(users -> {
            log.info("User roles for {}: {}", username, users.getRoles());
            return new User(users.getUsername(), users.getPassword(), Collections.singleton(users.getRoles()));
        }).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
