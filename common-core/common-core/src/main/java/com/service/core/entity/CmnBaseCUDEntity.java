package com.service.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public abstract class CmnBaseCUDEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "CREATE_DATE")
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false, name = "UPDATE_DATE")
    private LocalDateTime updateDate = LocalDateTime.now();

    @CreatedBy
    @Column(name = "CREATE_USER")
    private String createUser;

    @LastModifiedBy
    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "DELETE_DATE")
    private LocalDateTime deleteDate;

    @Column(name = "DELETE_USER")
    private String deleteUser;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    // 소프트 삭제를 위한 메서드
    public void delete(String userId) {
        this.useYn = "N";
        this.deleteDate = LocalDateTime.now();
        this.deleteUser = userId;
    }

    // 삭제 여부 확인
    public boolean isDeleted() {
        return "N".equals(this.useYn);
    }

    // 삭제 취소
    public void restore() {
        this.useYn = "Y";
        this.deleteDate = null;
        this.deleteUser = null;
    }
}