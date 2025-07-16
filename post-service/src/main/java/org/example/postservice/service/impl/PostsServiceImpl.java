package org.example.postservice.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.dto.*;
import org.example.postservice.mapper.PostsMapper;
import org.example.postservice.models.PostsReason;
import org.example.postservice.models.PostsStatus;
import org.example.postservice.openfeign.AnalyticPostFeign;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsServiceImpl implements PostsService {
    @Value("${post.length}")
    private Integer maxLength;
    private final BannedWordsFeign bannedWordsFeign;
    private final PostsMapper mapper;
    private final PostsRepository repository;
    private final AnalyticPostFeign analyticPostFeign;

    @Override
    @Transactional
    public PostsDTOResponse processingPosts(PostsDTORequest postsDTORequest, Authentication authentication) {
        var requestPost = mapper.toEntity(postsDTORequest);
        requestPost.setUserId(Integer.parseInt(authentication.getName()));
        if(postsDTORequest.content().length() > maxLength){
            requestPost.setStatus(PostsStatus.DECLINED);
            var analyticPost = mapper.toDeclanedAnalyticDTO(requestPost, PostsReason.LENGTH_EXCEEDED, Set.of());
            analyticPostFeign.savePost(analyticPost);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Post '%s' exceeds max length (%d > %d)"
                            .formatted(postsDTORequest.title(), postsDTORequest.content().length(), maxLength));
        }
        try {
            var setBannedWords = bannedWordsFeign.containsBannedWord(postsDTORequest).getBody();
            if(!setBannedWords.isEmpty()){
                requestPost.setStatus(PostsStatus.DECLINED);
                var analyticPost = mapper.toDeclanedAnalyticDTO(requestPost, PostsReason.BANNED_WORDS, setBannedWords);
                analyticPostFeign.savePost(analyticPost);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Post '%s' contains banned words: %s".formatted(postsDTORequest.title(), setBannedWords));
            }
        } catch (FeignException e) {
            log.error("Error calling banned-words-service: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Content validation service unavailable");
        }
        requestPost.setStatus(PostsStatus.PUBLISHED);
        var savedPost = repository.save(requestPost);
        var analyticPost = mapper.toPublishedAnalyticDTO(savedPost, PostsReason.NONE, Set.of());
        analyticPostFeign.savePost(analyticPost);
        return mapper.toResponse(savedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersPostDTO> findAllUsersPosts(Authentication authentication) {
        log.info("Finding all posts for user with id: {}",authentication.getName());
        var result = repository.findAllByUserId(Integer.valueOf(authentication.getName())).stream().map(mapper::toUsersPostsDTO).toList();
        log.info("Found {} posts for user {}", result.size(), authentication.getName());
        return result;
    }

}