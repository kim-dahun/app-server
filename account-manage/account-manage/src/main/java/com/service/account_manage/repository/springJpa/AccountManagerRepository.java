package com.service.account_manage.repository.springJpa;

import com.service.account_manage.entity.AccountManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountManagerRepository extends JpaRepository<AccountManager, Long> {
}