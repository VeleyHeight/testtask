package org.example.analyticpostservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.analyticpostservice.dto.AnalyticDTO;
import org.example.analyticpostservice.models.PostsReason;
import org.example.analyticpostservice.service.AnalyticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analytic/post")
public class AnalyticController {
    private final AnalyticService analyticService;

    @PostMapping
    public ResponseEntity<AnalyticDTO> saveAnalyticPost(@Valid @RequestBody AnalyticDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(analyticService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Integer> getCountForUsersPost(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(analyticService.findCountForUser(id));
    }

    @GetMapping
    public ResponseEntity<Long> getCountForAllPosts() {
        return ResponseEntity.ok().body(analyticService.findCountForAll());
    }

    @GetMapping("/banned/{id}")
    public ResponseEntity<Integer> getCountForUsersBannedPosts(@PathVariable Integer id) {
        return ResponseEntity.ok(analyticService.findCountBannedForUser(id));
    }

    @GetMapping("/banned")
    public ResponseEntity<Integer> getCountForAllBannedPost() {
        return ResponseEntity.ok().body(analyticService.findCountBannedForAll());
    }

    @GetMapping("/approved/{id}")
    public ResponseEntity<Integer> getCountForUsersApprovedPost(@PathVariable Integer id) {
        return ResponseEntity.ok().body(analyticService.findCountApprovedForUser(id));
    }

    @GetMapping("/approved")
    public ResponseEntity<Integer> getCountForAllApprovedPost() {
        return ResponseEntity.ok().body(analyticService.findCountApprovedForAll());
    }

    @GetMapping("/reason/{id}")
    public ResponseEntity<PostsReason> getCountForUsersReasonPost(@PathVariable Integer id) {
        return ResponseEntity.ok().body(analyticService.findMostPopularReasonForUser(id));
    }

    @GetMapping("/reason")
    public ResponseEntity<PostsReason> getCountForAllReasonPost() {
        return ResponseEntity.ok().body(analyticService.findMostPopularReasonForAll());
    }

    @GetMapping("/most-banned/{id}")
    public ResponseEntity<Set<String>> getCountForUsersMostBannedPost(@PathVariable Integer id) {
        return ResponseEntity.ok().body(analyticService.findMostBannedWordForUser(id));
    }

    @GetMapping("/most-banned")
    public ResponseEntity<Set<String>> getCountForAllMostBannedPost() {
        return ResponseEntity.ok().body(analyticService.findMostBannedWordForAll());
    }
}