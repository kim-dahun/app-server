package com.service.account_manage.entity;

import com.service.account_manage.entity.id.AccountManagerId;
import com.service.core.entity.CmnBaseCUDEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "INCOME_EXPENDITURE_LIST")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(AccountManagerId.class)
@DynamicUpdate
public class AccountManager extends CmnBaseCUDEntity {

    @Id
    @Column(name = "TRANSACTION_ID", length = 100)
    private String transactionId;

    @Id
    @Column(name = "COM_CD", length = 40)
    private String comCd;

    @Id
    @Column(name = "USER_ID", length = 20)
    private String userId;

    @Id
    @Column(name = "TRANSACTION_TIMEKEY")
    private String transactionTimekey;;

    @Column(name = "PROJECT_CODE",length = 20)
    private String projectCode;

    @Builder.Default
    @Column(name = "AMOUNT")
    private Double amount = 0.0;

    @Column(name = "TRADE_TYPE", length = 20)
    private String tradeType;

    @Column(name = "TRADE_TARGET", length = 100)
    private String tradeTarget;

    @Column(name = "TRADE_TARGET_DESC", length = 100)
    private String tradeTargetDesc;

    @Column(name = "ACCOUNT_CODE", length = 20)
    private String codeId;

    @Column(name = "REMARK", length = 200)
    private String remark;

    @Column(name = "TRANSACTION_DATE", length = 20)
    private String transactionDate;


    // 거래 금액 수정
    public void changeAmount(Double amount) {
        this.amount = amount;
    }

    // 거래 유형 변경
    public void changeTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    // 장부 코드 정보 변경
    public void changeAccountInfo(String codeId) {
        this.codeId = codeId;
    }

    // 비고 수정
    public void updateRemark(String remark) {
        this.remark = remark;
    }


    public void changeTransactionDate(String transactionDate){
        this.transactionDate = transactionDate;
    }


    // 전체 거래 정보 수정이 필요한 경우를 위한 메서드
    public void updateTransaction(Double amount, String tradeType, String codeId, String remark, String transactionDate, String projectCode) {
        this.amount = amount;
        this.tradeType = tradeType;
        this.codeId = codeId;
        this.remark = remark;
        this.projectCode = projectCode;
        this.transactionDate = transactionDate;
    }

}