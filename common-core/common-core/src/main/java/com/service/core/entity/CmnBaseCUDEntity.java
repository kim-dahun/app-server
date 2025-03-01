package com.service.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CmnBaseCUDEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "CREATE_DATE")
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false, name = "UPDATE_DATE")
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "DELETE_DATE")
    private LocalDateTime deleteDate;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    // Getters and Setters
    protected LocalDateTime getCreateDate() {
        return createDate;
    }

    protected void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    protected LocalDateTime getUpdateDate() {
        return updateDate;
    }

    protected void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    // 소프트 삭제를 위한 메서드
    public void delete() {
        this.useYn = "N";
        this.deleteDate = LocalDateTime.now();
    }

    // 삭제 여부 확인
    public boolean isDeleted() {
        return "N".equals(this.useYn);
    }

    // 삭제 취소
    public void restore() {
        this.useYn = "Y";
        this.deleteDate = null;
    }
}