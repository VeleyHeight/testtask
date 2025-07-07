package org.example.usersservice.mapper;

import org.example.usersservice.models.Users;
import org.example.usersservice.usersDTO.UsersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsersMapper {
    UsersDTO toDTO(Users users);
    Users toUsers(UsersDTO usersDTO);
}
