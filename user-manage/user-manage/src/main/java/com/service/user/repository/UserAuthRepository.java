package com.service.user.repository;

import com.service.user.entity.UserAuth;
import com.service.user.entity.id.UserAuthId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, UserAuthId> {
}