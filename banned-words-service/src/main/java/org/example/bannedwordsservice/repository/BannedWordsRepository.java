package org.example.bannedwordsservice.repository;

import org.example.bannedwordsservice.models.BannedWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedWordsRepository extends JpaRepository<BannedWords, Integer> {
    boolean existsByWord(String content);
    void deleteByWord(String word);
}
