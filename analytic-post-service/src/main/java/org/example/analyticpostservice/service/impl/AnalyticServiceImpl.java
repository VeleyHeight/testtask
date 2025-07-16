package org.example.analyticpostservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticpostservice.dto.AnalyticDTO;
import org.example.analyticpostservice.mapper.AnalyticMapper;
import org.example.analyticpostservice.models.PostsReason;
import org.example.analyticpostservice.models.PostsStatus;
import org.example.analyticpostservice.repository.AnalyticRepository;
import org.example.analyticpostservice.service.AnalyticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticServiceImpl implements AnalyticService {
    private final AnalyticRepository analyticRepository;
    private final AnalyticMapper analyticMapper;

    @Override
    @Transactional
    public AnalyticDTO save(AnalyticDTO analytic) {
        log.info("Saving analytic: {}", analytic);
        var savedEntity = analyticMapper.toEntity(analytic);
        var resultEntity = analyticRepository.save(savedEntity);
        log.info("Saved analytic: {}, successfully", resultEntity);
        return analyticMapper.toDTO(resultEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findCountForUser(Integer userId) {
        log.info("Finding count of posts for user: {}", userId);
        var count = analyticRepository.countByUserId(userId);
        log.info("Found {} posts for user: {}", count, userId);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findCountForAll() {
        log.info("Finding count of posts for all users");
        var count = analyticRepository.count();
        log.info("Found {} posts", count);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findCountBannedForUser(Integer userId) {
        log.info("Finding count of banned posts for users: {}", userId);
        var count = analyticRepository.countByUserIdAndStatus(userId, PostsStatus.DECLINED);
        log.info("Found {} banned posts for user: {}", count, userId);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findCountBannedForAll() {
        log.info("Finding count of banned posts for all users");
        var count = analyticRepository.countByStatus(PostsStatus.DECLINED);
        log.info("Found {} banned posts for all users", count);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findCountApprovedForUser(Integer userId) {
        log.info("Finding count of approved posts for user: {}", userId);
        var count = analyticRepository.countByUserIdAndStatus(userId, PostsStatus.PUBLISHED);
        log.info("Found {} approved posts for user: {}", count, userId);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findCountApprovedForAll() {
        log.info("Finding count of approved posts for all users");
        var count = analyticRepository.countByStatus(PostsStatus.PUBLISHED);
        log.info("Found {} approved posts for all users", count);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public PostsReason findMostPopularReasonForUser(Integer userId) {
        log.info("Finding most popular reason to decline post for user: {}", userId);
        var reason = analyticRepository.findMostPopularReasonForUser(userId);
        log.info("Most popular reason found for user: {}", userId);
        return reason;
    }

    @Override
    @Transactional(readOnly = true)
    public PostsReason findMostPopularReasonForAll() {
        log.info("Finding most popular reason to decline post for all users");
        var reason = analyticRepository.findMostPopularReasonForAll();
        log.info("Most popular reason found for all");
        return reason;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> findMostBannedWordForUser(Integer userId) {
        log.info("Finding most banned words for user: {}", userId);
        var words = analyticRepository.findMostBannedWordForUser(userId);
        log.info("Most banned words found for user: {}", userId);
        return words;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> findMostBannedWordForAll() {
        log.info("Finding most banned words for all users");
        var words = analyticRepository.findMostBannedWordForAll();
        log.info("Most banned words found for all");
        return words;
    }
}