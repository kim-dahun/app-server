package com.service.account_manage.repository;

import com.service.account_manage.entity.AccountCodeDetail
import com.service.account_manage.entity.id.AccountCodeDetailId
import org.springframework.data.jpa.repository.JpaRepository

interface AccountCodeDetailRepository : JpaRepository<AccountCodeDetail, AccountCodeDetailId> {
}