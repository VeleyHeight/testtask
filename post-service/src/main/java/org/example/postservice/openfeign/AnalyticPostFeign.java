package org.example.postservice.openfeign;


import org.example.postservice.dto.AnalyticDTO;
import org.example.postservice.dto.PostsDTORequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "analytic-post-service")
public interface AnalyticPostFeign {
    @PostMapping("/api/analytic/post")
    ResponseEntity<AnalyticDTO> savePost(AnalyticDTO analyticDTO);
}