package com.service.account_manage.entity;

import com.service.core.entity.CmnBaseCUDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INCOME_EXPENDITURE_LIST")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountManager extends CmnBaseCUDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long id;

    @Column(name = "USER_ID", length = 40)
    private String userId;

    @Column(name = "AMOUNT")
    private Double amount = 0.0;

    @Column(name = "TYPE", length = 10)
    private String type;

    @Column(name = "TRADE_TYPE", length = 20)
    private String tradeType;

    @Column(name = "ACCOUNT_CODE", length = 20)
    private String accountCode;

    @Column(name = "REMARK", length = 200)
    private String remark;

}