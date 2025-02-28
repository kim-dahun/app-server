package com.service.account_manage.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AccountCodeDetailId(
    @Column(name = "USER_ID", length = 20, nullable = false)
    val userId : String?= null,

    @Column(name = "CODE_ID", length = 20, nullable = false)
    val codeId : String?= null,

    @Column(name = "CODE_GROUP", length = 20, nullable = false)
    val codeGroup : String?= null

): Serializable
