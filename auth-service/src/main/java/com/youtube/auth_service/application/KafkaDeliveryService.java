package com.youtube.auth_service.application;

import java.util.List;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.youtube.auth_service.application.port.IKafkaDeliveryService;
import com.youtube.auth_service.internal.domain.models.OutboxEventModel;
import com.youtube.auth_service.internal.repo.OutBoxRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDeliveryService implements IKafkaDeliveryService {

    private final OutBoxRepository outBoxRepository;
    private final StreamBridge streamBridge;

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void processOutbox(){
        List<OutboxEventModel> events = outBoxRepository.findPendingWithLock();

        if (events.isEmpty()){
            log.debug("[KafkaDeliveryService]");
            return;
        }

        for(OutboxEventModel event : events){
            try {

                boolean sent = streamBridge.send(event.getTopic(), event.getPayload());

                if(sent){
                    event.setProcessed(true);
                    outBoxRepository.save(event);
                    log.info("Successfully send event", event.getAggregateId());
                }else{
                    log.warn("Kafka didn't accept the message for aggregate: {}", event.getId());
                }

            } catch (Exception e) {
                log.error("Failed to deliver event {}: {}", event.getId(), e.getMessage());
            }
        }
    }

}
