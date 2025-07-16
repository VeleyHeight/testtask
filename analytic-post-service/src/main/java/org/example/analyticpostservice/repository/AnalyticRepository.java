package org.example.analyticpostservice.repository;

import org.example.analyticpostservice.models.AnalyticPost;
import org.example.analyticpostservice.models.PostsReason;
import org.example.analyticpostservice.models.PostsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public interface AnalyticRepository extends JpaRepository<AnalyticPost, Integer> {
    Integer countByUserId(int userId);
    long count();
    Integer countByUserIdAndStatus(int userId, PostsStatus status);
    Integer countByStatus(PostsStatus status);
    @Query("""
            SELECT a.reason \
            FROM AnalyticPost a \
            WHERE a.userId = :userId AND a.reason != "NONE" \
            GROUP BY a.reason \
            ORDER BY COUNT(a.reason) DESC \
            LIMIT 1""")
    PostsReason findMostPopularReasonForUser(@Param("userId")Integer userId);
    @Query("""
            SELECT a.reason \
            FROM AnalyticPost a \
            WHERE a.reason != "NONE" \
            GROUP BY a.reason \
            ORDER BY COUNT(a.reason) DESC \
            LIMIT 1""")
    PostsReason findMostPopularReasonForAll();

    @Query("""
    SELECT word
    FROM AnalyticPost a
    JOIN a.bannedWords word
    WHERE a.userId = :userId
    GROUP BY word
    ORDER BY COUNT(word) DESC
    LIMIT 10""")
    LinkedHashSet<String> findMostBannedWordForUser(@Param("userId")Integer userId);

    @Query("""
    SELECT word
    FROM AnalyticPost a
    JOIN a.bannedWords word
    GROUP BY word
    ORDER BY COUNT(word) DESC
    LIMIT 10""")
    LinkedHashSet<String> findMostBannedWordForAll();

}