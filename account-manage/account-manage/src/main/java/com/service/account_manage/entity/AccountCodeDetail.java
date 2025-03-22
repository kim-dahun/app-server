package com.service.account_manage.entity;

import com.service.account_manage.entity.id.AccountCodeDetailId;
import com.service.core.entity.CmnBaseCUDEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ACCOUNT_CODE_DETAIL")
@IdClass(AccountCodeDetailId.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCodeDetail extends CmnBaseCUDEntity {

    @Id
    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    @Id
    @Column(name = "CODE_ID", length = 20, nullable = false)
    private String codeId;

    @Id
    @Column(name = "CODE_GROUP", length = 20, nullable = false)
    private String codeGroup;

    @Column(name = "CODE_DESC", length = 40)
    private String codeDesc;

    @Column(name = "PARENT_CODE", length = 20)
    private String parentCode;

    @Column(name = "CODE_LEVEL")
    private Integer level;

    @Column(name = "SORT_SEQ")
    private Integer sortSeq;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false),
            @JoinColumn(name = "CODE_GROUP", referencedColumnName = "CODE_ID", nullable = false)
    })
    private AccountCode accountCode;

    public void createCodeId(){
        if(this.codeId == null){
            this.codeId = this.codeGroup + "_" + this.codeId;
        }
    }

    public void updateParentCode(String parentCode, Integer level){
        this.parentCode = parentCode;
        this.level = level;
    }

    public void updateCodeDesc(String codeDesc){
        this.codeDesc = codeDesc;
    }

}