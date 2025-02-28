package com.service.account_manage.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AccountCodeId(
    @Column(name = "USER_ID", length = 20, nullable = false)
    val userId : String?= null,

    @Column(name = "CODE_ID", length = 20)
    val codeId : String?= null
):Serializable
