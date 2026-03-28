package com.youtube.auth_service.internal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.youtube.auth_service.internal.domain.models.OutboxEventModel;

@Repository
public interface OutBoxRepository extends JpaRepository<OutboxEventModel, Long>{
    
    @Query(value = "SELECT * FROM outbox_events WHERE is_processed = false " + "LIMIT 10 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    List<OutboxEventModel> findPendingWithLock();
}
