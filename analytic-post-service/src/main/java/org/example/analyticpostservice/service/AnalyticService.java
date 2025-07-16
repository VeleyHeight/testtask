package org.example.analyticpostservice.service;

import org.example.analyticpostservice.dto.AnalyticDTO;
import org.example.analyticpostservice.models.PostsReason;

import java.util.Set;

public interface AnalyticService {
    AnalyticDTO save(AnalyticDTO analytic);
    Integer findCountForUser(Integer userId);
    Long findCountForAll();
    Integer findCountBannedForUser(Integer userId);
    Integer findCountBannedForAll();
    Integer findCountApprovedForUser(Integer userId);
    Integer findCountApprovedForAll();
    PostsReason findMostPopularReasonForUser(Integer userId);
    PostsReason findMostPopularReasonForAll();
    Set<String> findMostBannedWordForUser(Integer userId);
    Set<String> findMostBannedWordForAll();
}
