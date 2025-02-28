package com.service.account_manage.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "INCOME_EXPENDITURE_LIST")
class AccountManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    val id: Long? = null;

    @Column(name = "USER_ID", length = 40)
    val userId: String? = null;

    @Column(name = "AMOUNT")
    var amount : Double = 0.0;

    @Column(name = "USE_YN", length = 1)
    var useYn : String = "Y";

    @Column(name = "DELETE_DATE")
    var deleteDate : LocalDateTime? = null;

    @Column(name = "CREATE_DATE")
    var createDate : LocalDateTime? = LocalDateTime.now();

    @Column(name = "UPDATE_DATE")
    var updateDate : LocalDateTime? = LocalDateTime.now();

    @Column(name = "TYPE", length = 10)
    var type : String? = null;

    @Column(name = "TRADE_TYPE", length = 20)
    var tradeType : String? = null;

    @Column(name = "ACCOUNT_CODE", length = 20)
    var accountCode : String? = null;

    @Column(name = "REMARK", length = 200)
    var remark : String? = null;


}