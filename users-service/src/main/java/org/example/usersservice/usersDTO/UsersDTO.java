package org.example.usersservice.usersDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record UsersDTO(@Valid @Size(min = 10, max = 50, message = "Username size must be between 10 and 50") String username,
                       @Size(min = 10, max = 50, message = "Password size must be between 10 and 50") String password,
                       @Size(min = 10, max = 50, message = "Email size must be between 10 and 50") String email) {
}
