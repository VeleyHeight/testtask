package org.example.bannedwordsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bannedwordsservice.dto.BannedWordsDTORequest;
import org.example.bannedwordsservice.dto.BannedWordsDTOResponse;
import org.example.bannedwordsservice.mapper.BannedWordsMapper;
import org.example.bannedwordsservice.models.BannedWords;
import org.example.bannedwordsservice.repository.BannedWordsRepository;
import org.example.bannedwordsservice.service.BannedWordsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannedWordsServiceImpl implements BannedWordsService {
    private final BannedWordsRepository bannedWordsRepository;
    private final BannedWordsMapper bannedWordsMapper;

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllBannedWords() {
        log.info("Get all banned words");
        return bannedWordsRepository.findAll().stream().map(BannedWords::getWord).toList();
    }

    @Override
    @Transactional
    public BannedWordsDTOResponse save(BannedWordsDTORequest word) {
      log.info("Saving word: " + word);
      if(bannedWordsRepository.existsByWord(word.word())){
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Word already exists");
      }
      var savedWord = bannedWordsMapper.toBannedWords(word);
      bannedWordsRepository.save(savedWord);
      log.info("Saved word: " + word);
      return bannedWordsMapper.toBannedWordsDTO(savedWord);
    }

    @Override
    @Transactional
    public void delete(BannedWordsDTORequest word) {
        if(bannedWordsRepository.existsByWord(word.word())){
            bannedWordsRepository.deleteByWord(word.word());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Word does not exist");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBanned(BannedWordsDTORequest word) {
        log.info("Checking if banned word: " + word);
        return bannedWordsRepository.existsByWord(word.word());
    }
}
