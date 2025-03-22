package com.service.account_manage.api.accoun_code.vo.data;

import com.service.account_manage.entity.AccountCodeDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Setter
public class AccountCodeDetailVo extends AccountCodeVo{

    private String parentCode;
    private String codeGroup;
    private Integer level;
    private Integer sortSeq;
    private List<AccountCodeDetail> childList;

    public AccountCodeDetail toAccountCodeDetailEntity(){
        return AccountCodeDetail.builder()
                .codeId(codeId)
                .userId(userId)
                .codeDesc(codeDesc)
                .parentCode(parentCode)
                .codeGroup(codeGroup)
                .level(level)
                .sortSeq(sortSeq)
                .build();
    }

    public static AccountCodeDetailVo fromAccountCodeDetailEntity(AccountCodeDetail accountCodeDetail){
        return AccountCodeDetailVo.builder()
                .codeId(accountCodeDetail.getCodeId())
                .userId(accountCodeDetail.getUserId())
                .codeDesc(accountCodeDetail.getCodeDesc())
                .parentCode(accountCodeDetail.getParentCode())
                .codeGroup(accountCodeDetail.getCodeGroup())
                .level(accountCodeDetail.getLevel())
                .sortSeq(accountCodeDetail.getSortSeq())
                .build();
    }

}
