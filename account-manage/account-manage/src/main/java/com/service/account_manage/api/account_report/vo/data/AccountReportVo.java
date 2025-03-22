package com.service.account_manage.api.account_report.vo.data;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
import com.service.account_manage.entity.AccountCodeDetail;
import com.service.account_manage.entity.AccountManager;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AccountReportVo extends AccountCodeDetailVo {

    private String codeId;

    private Double amount;
    private String type;
    private String transactionId;
    private String userId;
    private String tradeType;
    private String remark;
    private String transactionDate;
    private String tradeCode;

    public AccountManager toEntity(){
        return AccountManager
                .builder()
                .transactionId(transactionId)
                .remark(remark)
                .userId(userId)
                .type(type)
                .tradeType(tradeType)
                .amount(amount)
                .codeId(codeId)
                .transactionDate(transactionDate)
                .tradeCode(tradeCode)
                .comCd(comCd)
                .build();
    }

    public static AccountReportVo fromEntity(AccountManager accountManager){
        return AccountReportVo
                .builder()
                .transactionId(accountManager.getTransactionId())
                .codeId(accountManager.getCodeId())
                .amount(accountManager.getAmount())
                .type(accountManager.getType())
                .userId(accountManager.getUserId())
                .tradeType(accountManager.getTradeType())
                .remark(accountManager.getRemark())
                .transactionDate(accountManager.getTransactionDate())
                .tradeCode(accountManager.getTradeCode())
                .comCd(accountManager.getComCd())
                .build();
    }

}
