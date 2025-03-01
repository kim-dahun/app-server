package com.service.account_manage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class AccountManageApplication

fun main(args: Array<String>) {
    runApplication<AccountManageApplication>(*args)
}
