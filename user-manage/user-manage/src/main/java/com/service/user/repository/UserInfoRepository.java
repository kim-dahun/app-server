package com.service.user.repository;

import com.service.user.entity.UserInfo;
import com.service.user.entity.id.UserInfoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, UserInfoId> {
}