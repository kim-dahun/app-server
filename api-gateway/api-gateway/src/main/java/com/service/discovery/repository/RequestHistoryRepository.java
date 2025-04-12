package com.service.discovery.repository;

import com.service.discovery.entity.RequestHistory;
import com.service.discovery.entity.id.RequestHistoryId;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RequestHistoryRepository extends R2dbcRepository<RequestHistory, RequestHistoryId> {

}
