package org.example.bannedwordsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bannedwordsservice.dto.BannedWordsDTORequest;
import org.example.bannedwordsservice.dto.BannedWordsDTOResponse;
import org.example.bannedwordsservice.dto.CheckBannedWordsDTORequest;
import org.example.bannedwordsservice.mapper.BannedWordsMapper;
import org.example.bannedwordsservice.models.BannedWords;
import org.example.bannedwordsservice.repository.BannedWordsRepository;
import org.example.bannedwordsservice.service.BannedWordsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannedWordsServiceImpl implements BannedWordsService {
    private final BannedWordsRepository bannedWordsRepository;
    private final BannedWordsMapper bannedWordsMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<String> getAllBannedWords() {
        log.info("Get all banned words");
        return bannedWordsRepository.findAll().stream().map(BannedWords::getWord).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public BannedWordsDTOResponse save(BannedWordsDTORequest request) {
      log.info("Saving word: " + request.word());
      if(bannedWordsRepository.existsByWord(request.word().toLowerCase())){
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Word already exists");
      }
      var savedWord = bannedWordsMapper.toBannedWords(request);
      savedWord.setWord(savedWord.getWord().toLowerCase());
      bannedWordsRepository.save(savedWord);
      log.info("Saved word: " + request.word());
      return bannedWordsMapper.toBannedWordsDTO(savedWord);
    }

    @Override
    @Transactional
    public void delete(BannedWordsDTORequest request) {
        if(bannedWordsRepository.existsByWord(request.word())){
            bannedWordsRepository.deleteByWord(request.word());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Word does not exist");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> isBanned(CheckBannedWordsDTORequest request) {
        log.info("Checking banned word in post: " + request.title());
        var listBannedWords = bannedWordsRepository.findAll().stream().map(word -> word.getWord().toLowerCase()).collect(Collectors.toSet());
        var containingFromContent = Arrays.stream(request.content().toLowerCase().split("\\W+"))
                .filter(word -> !word.isBlank() && listBannedWords.contains(word.toLowerCase()))
                .collect(Collectors.toSet());
        var containingFromTitle = Arrays.stream(request.title().toLowerCase().split("\\W+"))
                .filter(word -> !word.isBlank() && listBannedWords.contains(word.toLowerCase()))
                .collect(Collectors.toSet());
        var resultSet = new HashSet<>(containingFromContent);
        resultSet.addAll(containingFromTitle);
        log.info("Post {} contains {} banned words", request.title(), resultSet.size());
        return resultSet;
    }
}
