package com.service.account_manage.api.accoun_code.vo.data;

import com.service.account_manage.entity.AccountCodeDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Setter
public class AccountCodeDetailVo extends AccountCodeVo{

    private String parentCode;
    private String codeGroup;
    private Integer level;

    public AccountCodeDetail toEntity(){
        return AccountCodeDetail.builder()
                .codeId(this.getCodeId())
                .userId(this.getUserId())
                .codeDesc(this.getCodeDesc())
                .parentCode(this.getParentCode())
                .codeGroup(this.getCodeGroup())
                .level(this.getLevel())
                .build();
    }

    public static AccountCodeDetailVo fromEntity(AccountCodeDetail accountCodeDetail){
        return AccountCodeDetailVo.builder()
                .codeId(accountCodeDetail.getCodeId())
                .userId(accountCodeDetail.getUserId())
                .codeDesc(accountCodeDetail.getCodeDesc())
                .parentCode(accountCodeDetail.getParentCode())
                .codeGroup(accountCodeDetail.getCodeGroup())
                .level(accountCodeDetail.getLevel())
                .build();
    }

}
