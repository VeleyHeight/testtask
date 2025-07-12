package org.example.postservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.dto.PostsDTORequest;
import org.example.postservice.dto.PostsDTOResponse;
import org.example.postservice.dto.UsersPostDTO;
import org.example.postservice.mapper.PostsMapper;
import org.example.postservice.models.PostsStatus;
import org.example.postservice.openfeign.BannedWordsFeign;
import org.example.postservice.repository.PostsRepository;
import org.example.postservice.service.PostsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsServiceImpl implements PostsService {
    @Value("${post.length}")
    private Integer maxLength;
    private final BannedWordsFeign bannedWordsFeign;
    private final PostsMapper mapper;
    private final PostsRepository repository;

    @Override
    @Transactional
    public PostsDTOResponse processingPosts(PostsDTORequest postsDTORequest, Authentication authentication) {
        if(postsDTORequest.content().length() > maxLength){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max length of post is %s".formatted(maxLength));
        }
        var requestPost = mapper.toEntity(postsDTORequest);
        requestPost.setUserId(Integer.parseInt(authentication.getName()));
        var setBannedWords = bannedWordsFeign.containsBannedWord(postsDTORequest).getBody();
        if(!setBannedWords.isEmpty()){
            requestPost.setStatus(PostsStatus.DECLINED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post contain banned word : %s".formatted(setBannedWords));
        }
        requestPost.setStatus(PostsStatus.PUBLISHED);
        repository.save(requestPost);
        return mapper.toResponse(requestPost);
    }

    @Override
    public List<UsersPostDTO> findAllUsersPosts(Authentication authentication) {
        log.info("Finding all posts for user with id: {}",authentication.getName());
        var result = repository.findAllByUserId(Integer.valueOf(authentication.getName())).stream().map(mapper::toUsersPostsDTO).toList();
        log.info("Found {} posts for user {}",result.size(), authentication.getName());
        return result;
    }

}
