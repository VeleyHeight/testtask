package org.example.bannedwordsservice.service;

import org.example.bannedwordsservice.dto.BannedWordsDTORequest;
import org.example.bannedwordsservice.dto.BannedWordsDTOResponse;

import java.util.List;

public interface BannedWordsService {
    List<String> getAllBannedWords();
    BannedWordsDTOResponse save(BannedWordsDTORequest word);
    void delete(BannedWordsDTORequest word);
    boolean isBanned(BannedWordsDTORequest word);
}
