package com.service.account_manage.repository.springJpa;

import com.service.account_manage.entity.AccountManager;
import com.service.account_manage.entity.id.AccountManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountManagerRepository extends JpaRepository<AccountManager, AccountManagerId> {
    Boolean existsByCodeIdAndUserId(String codeId, String userId);

    boolean existsByCodeIdInAndUserId(List<String> codeIds, String userId);
}