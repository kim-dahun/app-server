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
    private String codeDesc;

    private Double amount;
    private String transactionId;
    private String userId;

    private String tradeType;
    private String tradeTypeDesc;

    private String remark;
    private String transactionDate;
    private String projectCode;
    private String projectDesc; //

    private String tradeTarget;
    private String tradeTargetDesc;

    public AccountManager toEntity(){
        return AccountManager
                .builder()
                .transactionId(transactionId)
                .remark(remark)
                .userId(userId)
                .tradeType(tradeType)
                .amount(amount)
                .codeId(codeId)
                .transactionDate(transactionDate)
                .projectCode(projectCode)
                .comCd(comCd)
                .tradeTarget(tradeTarget)
                .tradeTargetDesc(tradeTargetDesc)
                .build();
    }

    public static AccountReportVo fromEntity(AccountManager accountManager){
        return AccountReportVo
                .builder()
                .transactionId(accountManager.getTransactionId())
                .codeId(accountManager.getCodeId())
                .amount(accountManager.getAmount())
                .userId(accountManager.getUserId())
                .tradeType(accountManager.getTradeType())
                .remark(accountManager.getRemark())
                .transactionDate(accountManager.getTransactionDate())
                .projectCode(accountManager.getProjectCode())
                .comCd(accountManager.getComCd())
                .tradeTarget(accountManager.getTradeTarget())
                .tradeTargetDesc(accountManager.getTradeTargetDesc())
                .build();
    }

}
