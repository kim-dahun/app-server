package com.service.discovery.repository;

import com.service.discovery.entity.RequestHistory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestHistoryRepository extends R2dbcRepository<RequestHistory, String> {

}
