package com.service.account_manage.repository.springJpa;

import com.service.account_manage.entity.AccountManager
import org.springframework.data.jpa.repository.JpaRepository

interface AccountManagerRepository : JpaRepository<AccountManager, Long> {
}