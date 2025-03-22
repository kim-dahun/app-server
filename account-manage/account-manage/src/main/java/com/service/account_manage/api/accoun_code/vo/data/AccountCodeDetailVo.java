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

    protected String parentCode;
    protected String codeGroup;
    protected Integer level;
    protected List<AccountCodeDetailVo> childList;

    public AccountCodeDetail toAccountCodeDetailEntity(){
        return AccountCodeDetail.builder()
                .codeId(codeId)
                .userId(userId)
                .codeDesc(codeDesc)
                .parentCode(parentCode)
                .codeGroup(codeGroup)
                .level(level)
                .sortSeq(sortSeq)
                .comCd(comCd)
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
                .comCd(accountCodeDetail.getComCd())
                .build();
    }

}
