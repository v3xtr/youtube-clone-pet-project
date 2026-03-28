package com.youtube.auth_service.application;

import com.youtube.auth_service.application.port.IOutboxService;
import com.youtube.auth_service.internal.domain.entities.OutboxEventEntity;
import com.youtube.auth_service.internal.domain.models.OutboxEventModel;
import com.youtube.auth_service.internal.mappers.OutboxMapper;
import com.youtube.auth_service.internal.repo.OutBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxService implements IOutboxService {
    
    private final OutBoxRepository outBoxRepository;

    @Transactional
    public void saveEvent(OutboxEventEntity outboxEventEntity) {
        OutboxEventModel outBoxEventModel = OutboxMapper.toModel(outboxEventEntity);

        outBoxRepository.save(outBoxEventModel);
    }
}