package org.example.postservice.mapper;

import org.example.postservice.dto.*;
import org.example.postservice.models.Posts;
import org.example.postservice.models.PostsReason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.Clock;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {Clock.class})
public interface PostsMapper {
    Posts toEntity(PostsDTORequest postsDTORequest);
    PostsDTOResponse toResponse(Posts posts);
    UsersPostDTO toUsersPostsDTO(Posts posts);
    @Mapping(target = "createdAt", expression = "java(Instant.now(Clock.systemUTC()))")
    AnalyticDTO toDeclanedAnalyticDTO(Posts posts, PostsReason reason, Set<String> bannedWords);
    AnalyticDTO toPublishedAnalyticDTO(Posts posts, PostsReason reason, Set<String> bannedWords);
}