package com.service.account_manage.repository.springJpa;

import com.service.account_manage.entity.AccountCode;
import com.service.account_manage.entity.id.AccountCodeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountCodeRepository extends JpaRepository<AccountCode, AccountCodeId> {


    List<AccountCode> findByCodeIdInAndUserId(List<String> codeIds, String userId);
}