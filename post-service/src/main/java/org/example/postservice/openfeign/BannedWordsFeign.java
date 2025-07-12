package org.example.postservice.openfeign;

import org.example.postservice.dto.PostsDTORequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(value = "banned-words-service")
public interface BannedWordsFeign {
    @PostMapping("/api/banned-words/check")
    ResponseEntity<Set<String>> containsBannedWord(@RequestBody PostsDTORequest content);
}
