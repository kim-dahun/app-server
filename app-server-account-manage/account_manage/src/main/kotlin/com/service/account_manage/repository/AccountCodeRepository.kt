package com.service.account_manage.repository;

import com.service.account_manage.entity.AccountCode
import com.service.account_manage.entity.id.AccountCodeId
import org.springframework.data.jpa.repository.JpaRepository

interface AccountCodeRepository : JpaRepository<AccountCode, AccountCodeId> {
}