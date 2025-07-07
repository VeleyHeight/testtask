package org.example.usersservice.mapper;

import org.example.usersservice.models.Roles;
import org.example.usersservice.models.Users;
import org.example.usersservice.usersDTO.UsersDTO;
import org.example.usersservice.usersDTO.UsersLoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsersMapper {
    UsersDTO toDTO(Users users);
    @Mapping(target = "roles", expression = "java(org.example.usersservice.models.Roles.USER)")
    Users toUsers(UsersDTO usersDTO);
}
