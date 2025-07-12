package org.example.postservice.mapper;

import org.example.postservice.dto.PostsDTORequest;
import org.example.postservice.dto.PostsDTOResponse;
import org.example.postservice.dto.UsersPostDTO;
import org.example.postservice.models.Posts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostsMapper {
    Posts toEntity(PostsDTORequest postsDTORequest);
    PostsDTOResponse toResponse(Posts posts);
    UsersPostDTO toUsersPostsDTO(Posts posts);
}
