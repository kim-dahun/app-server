package com.service.discovery.repository;

import com.service.discovery.entity.RequestHistory;
import com.service.discovery.entity.id.RequestHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, RequestHistoryId> {

}