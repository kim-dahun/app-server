package com.service.account_manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AccountManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManageApplication.class, args);
    }

}
