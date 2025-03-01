package com.service.account_manage.entity

import com.service.account_manage.entity.id.AccountCodeId
import com.service.common_service.entity.CmnBaseCUDEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ACCOUNT_CODE_MASTER")
@IdClass(AccountCodeId::class)
class AccountCode:CmnBaseCUDEntity() {

    @Id
    var userId: String? = null;

    @Id
    val codeId: String? = null;

    @Column(name = "CODE_DESC", length = 40)
    var codeDesc: String? = null;

    @OneToMany(mappedBy = "accountCode", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val accountCodeDetailList : List<AccountCodeDetail> = mutableListOf();

}