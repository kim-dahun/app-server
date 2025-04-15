package com.service.user.repository;

import com.service.user.entity.ComInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComInfoRepository extends JpaRepository<ComInfo, String> {
}