package com.service.account_manage.repository.springJpa;

import com.service.account_manage.entity.AccountCode;
import com.service.account_manage.entity.AccountCodeDetail;
import com.service.account_manage.entity.id.AccountCodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountCodeDetailRepository extends JpaRepository<AccountCodeDetail, AccountCodeDetailId> {
    List<AccountCodeDetail> findByCodeGroupAndUserId(String codeId, String userId);
}