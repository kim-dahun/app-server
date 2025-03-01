package com.service.account_manage.entity

import com.service.account_manage.entity.id.AccountCodeDetailId
import com.service.common_service.entity.CmnBaseCUDEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ACCOUNT_CODE_DETAIL")
@IdClass(AccountCodeDetailId::class)
class AccountCodeDetail : CmnBaseCUDEntity() {

    @Id
    var userId: String? = null;

    @Id
    val codeId: String? = null;

    @Id
    val codeGroup : String? = null;

    @Column(name = "CODE_DESC", length = 40)
    var codeDesc: String? = null;

    @Column(name = "PARENT_CODE", length = 20)
    var parentCode: String? = null;

    @Column(name = "CODE_LEVEL")
    var level: Int? = null;

    // 복합 외래키 설정 (USER_ID + CODE_ID)
    @ManyToOne
    @JoinColumns(
        JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false),
        JoinColumn(name = "CODE_GROUP", referencedColumnName = "CODE_ID", nullable = false)
    )
    lateinit var accountCode: AccountCode

}