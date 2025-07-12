package org.example.postservice.service;

import org.example.postservice.dto.PostsDTORequest;
import org.example.postservice.dto.PostsDTOResponse;
import org.example.postservice.dto.UsersPostDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostsService {
    PostsDTOResponse processingPosts(PostsDTORequest postsDTORequest, Authentication authentication);
    List<UsersPostDTO> findAllUsersPosts(Authentication authentication);
}
