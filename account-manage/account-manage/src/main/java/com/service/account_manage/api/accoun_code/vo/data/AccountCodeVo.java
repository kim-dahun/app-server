package com.service.account_manage.api.accoun_code.vo.data;

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
    protected LocalDateTime createDate;
    protected LocalDateTime updateDate;
    protected LocalDateTime deleteDate;

    public AccountCodeVo toEntity(String codeId, String codeDesc, String userId, String useYn) {
        return AccountCodeVo.builder()
                .codeId(codeId)
                .codeDesc(codeDesc)
                .userId(userId)
                .useYn(useYn)
                .build();
    }

    public static AccountCodeDetailVo fromEntity(AccountCodeDetail accountCodeDetail){
        return AccountCodeDetailVo.builder()
                .codeId(accountCodeDetail.getCodeId())
                .codeDesc(accountCodeDetail.getCodeDesc())
                .userId(accountCodeDetail.getUserId())
                .useYn(accountCodeDetail.getUseYn())
                .build();
    }

}
