package org.example.bannedwordsservice.mapper;

import org.example.bannedwordsservice.dto.BannedWordsDTORequest;
import org.example.bannedwordsservice.dto.BannedWordsDTOResponse;
import org.example.bannedwordsservice.models.BannedWords;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BannedWordsMapper {
    BannedWords toBannedWords(BannedWordsDTORequest word);
    BannedWordsDTOResponse toBannedWordsDTO(BannedWords bannedWords);
}
