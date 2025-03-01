package com.service.account_manage.repository.springJpa;

import com.service.account_manage.entity.AccountCodeDetail;
import com.service.account_manage.entity.id.AccountCodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCodeDetailRepository extends JpaRepository<AccountCodeDetail, AccountCodeDetailId> {
}