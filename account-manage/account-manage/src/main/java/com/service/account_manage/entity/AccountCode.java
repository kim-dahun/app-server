package com.service.account_manage.entity;

import com.service.account_manage.entity.id.AccountCodeId;
import com.service.core.entity.CmnBaseCUDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_CODE_MASTER")
@IdClass(AccountCodeId.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCode extends CmnBaseCUDEntity {


    @Id
    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    @Id
    @Column(name = "CODE_ID", length = 20)
    private String codeId;

    @Column(name = "CODE_DESC", length = 40)
    private String codeDesc;

    @OneToMany(mappedBy = "accountCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountCodeDetail> accountCodeDetailList = new ArrayList<>();


}