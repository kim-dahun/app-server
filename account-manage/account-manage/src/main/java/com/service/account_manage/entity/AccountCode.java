package com.service.account_manage.entity;

import com.service.account_manage.entity.id.AccountCodeId;
import com.service.core.entity.CmnBaseCUDEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_CODE_MASTER")
@IdClass(AccountCodeId.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountCode extends CmnBaseCUDEntity {


    @Id
    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    @Id
    @Column(name = "CODE_ID", length = 20)
    private String codeId;

    @Id
    @Column(name = "COM_CD", length = 40)
    private String comCd;

    @Column(name = "CODE_DESC", length = 40)
    private String codeDesc;

    @Column(name = "SORT_SEQ")
    private Integer sortSeq;

    @Builder.Default
    @OneToMany(mappedBy = "accountCode", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<AccountCodeDetail> accountCodeDetailList = new ArrayList<>();

    public void updateCodeDesc(String codeDesc){
        this.codeDesc = codeDesc;
    }

}