package org.example.bannedwordsservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.bannedwordsservice.dto.BannedWordsDTORequest;
import org.example.bannedwordsservice.dto.BannedWordsDTOResponse;
import org.example.bannedwordsservice.dto.CheckBannedWordsDTORequest;
import org.example.bannedwordsservice.service.BannedWordsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banned-words")
public class BannedWordsController {
    private final BannedWordsService bannedWordsService;

    @GetMapping
    ResponseEntity<Set<String>> getBannedWords() {
        return ResponseEntity.ok(bannedWordsService.getAllBannedWords());
    }

    @PostMapping
    ResponseEntity<BannedWordsDTOResponse> addBannedWord(@Valid @RequestBody BannedWordsDTORequest word) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bannedWordsService.save(word));
    }

    @DeleteMapping("/{word}")
    ResponseEntity<Void> deleteBannedWords(@Valid @PathVariable BannedWordsDTORequest word) {
        bannedWordsService.delete(word);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/check")
    ResponseEntity<Set<String>> checkBannedWords(@Valid @RequestBody CheckBannedWordsDTORequest content) {
        return ResponseEntity.ok().body(bannedWordsService.isBanned(content));
    }
}