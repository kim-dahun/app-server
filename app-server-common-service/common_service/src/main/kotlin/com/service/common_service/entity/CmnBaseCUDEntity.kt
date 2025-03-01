package com.service.common_service.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.cglib.core.Local
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class CmnBaseCUDEntity{
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "CREATE_DATE")
    var createDate: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(nullable = false, name = "UPDATE_DATE")
    var updateDate: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(name = "DELETE_DATE")
    var deleteDate: LocalDateTime? = null

    @Column(name = "USE_YN", length = 1)
    var useYn: String? = null

    // 소프트 삭제를 위한 메서드
    fun delete() {
        this.useYn = "N";
        this.deleteDate = LocalDateTime.now();
    }

    // 삭제 여부 확인
    fun isDeleted(): Boolean {
        return this.useYn == "N";
    }

    // 삭제 취소
    fun restore() {
        this.useYn = "Y";
        this.deleteDate = null;
    }
}
