package com.service.account_manage.entity

import com.service.account_manage.entity.id.AccountCodeId
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ACCOUNT_CODE_MASTER")
@IdClass(AccountCodeId::class)
class AccountCode {

    @Id
    var userId: String? = null;

    @Id
    val codeId: String? = null;

    @Column(name = "CODE_DESC", length = 40)
    var codeDesc: String? = null;

    @Column(name = "CREATE_DATE", nullable = false)
    var createDate: LocalDateTime? = LocalDateTime.now();

    @Column(name = "UPDATE_DATE", nullable = false)
    var updateDate: LocalDateTime? = LocalDateTime.now();

    @Column(name = "USE_YN", length = 1, nullable = false)
    var useYn: String? = "Y";

    @OneToMany(mappedBy = "accountCode", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val accountCodeDetailList : List<AccountCodeDetail> = mutableListOf();

}