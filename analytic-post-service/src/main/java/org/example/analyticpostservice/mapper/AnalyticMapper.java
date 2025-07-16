package org.example.analyticpostservice.mapper;

import org.example.analyticpostservice.dto.AnalyticDTO;
import org.example.analyticpostservice.models.AnalyticPost;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnalyticMapper {
    AnalyticDTO toDTO(AnalyticPost analyticPost);
    AnalyticPost toEntity(AnalyticDTO analyticDTO);
}