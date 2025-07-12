package org.example.postservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.postservice.dto.PostsDTORequest;
import org.example.postservice.dto.PostsDTOResponse;
import org.example.postservice.dto.UsersPostDTO;
import org.example.postservice.service.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostsController {
    private final PostsService postsService;

    @PostMapping("/processing")
    public ResponseEntity<PostsDTOResponse> processingPosts(@Valid @RequestBody PostsDTORequest request){
        return ResponseEntity.ok().body(postsService.processingPosts(request, SecurityContextHolder.getContext().getAuthentication()));
    }

    @GetMapping("/publishing")
    public ResponseEntity<List<UsersPostDTO>> findAllUsersPost(){
        return ResponseEntity.ok().body(postsService.findAllUsersPosts(SecurityContextHolder.getContext().getAuthentication()));
    }
}
