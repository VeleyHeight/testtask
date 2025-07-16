package org.example.bannedwordsservice.service;

import org.example.bannedwordsservice.dto.BannedWordsDTORequest;
import org.example.bannedwordsservice.dto.BannedWordsDTOResponse;
import org.example.bannedwordsservice.dto.CheckBannedWordsDTORequest;

import java.util.List;
import java.util.Set;

public interface BannedWordsService {
    Set<String> getAllBannedWords();
    BannedWordsDTOResponse save(BannedWordsDTORequest word);
    void delete(BannedWordsDTORequest word);
    Set<String> isBanned(CheckBannedWordsDTORequest content);
}
