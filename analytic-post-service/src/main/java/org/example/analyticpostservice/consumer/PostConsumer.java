package org.example.analyticpostservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticpostservice.dto.AnalyticDTO;
import org.example.analyticpostservice.mapper.AnalyticMapper;
import org.example.analyticpostservice.repository.AnalyticRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostConsumer {
    private final AnalyticMapper analyticMapper;
    private final AnalyticRepository analyticRepository;

    @KafkaListener(topics = "post-event",
            properties = {"spring.json.value.default.type=org.example.analyticpostservice.dto.AnalyticDTO",
                    "spring.json.trusted.packages=org.example.analyticpostservice.dto"
    })
    @Transactional
    public void handleEvent(AnalyticDTO analytic){
        log.info("Saving analytic: {}", analytic);
        var savedEntity = analyticMapper.toEntity(analytic);
        var resultEntity = analyticRepository.save(savedEntity);
        log.info("Saved analytic: {}, successfully", resultEntity);
    }

}
