package com.service.account_manage.api.accoun_code.vo.data;

import com.service.account_manage.entity.AccountCode;
import com.service.account_manage.entity.AccountCodeDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Setter
public class AccountCodeVo {
    
    protected String codeId;
    protected String codeDesc;
    protected String userId;
    protected String useYn;
    protected Integer sortSeq;
    protected LocalDateTime createDate;
    protected LocalDateTime updateDate;
    protected LocalDateTime deleteDate;
    protected String comCd;

    public AccountCode toAccountCodeEntity() {
        return AccountCode.builder()
                .codeId(codeId)
                .codeDesc(codeDesc)
                .userId(userId)
                .sortSeq(sortSeq)
                .comCd(comCd)
                .build();
    }

    public static AccountCodeVo fromAccountCodeEntity(AccountCode accountCode){
        return AccountCodeVo.builder()
                .codeId(accountCode.getCodeId())
                .codeDesc(accountCode.getCodeDesc())
                .userId(accountCode.getUserId())
                .sortSeq(accountCode.getSortSeq())
                .comCd(accountCode.getComCd())
                .build();
    }

}
