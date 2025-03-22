package com.service.account_manage.entity;

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
@DynamicUpdate
public class AccountManager extends CmnBaseCUDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long id;

    @Column(name = "USER_ID", length = 40)
    private String userId;

    @Column(name = "AMOUNT")
    private Double amount = 0.0;

    @Column(name = "TYPE", length = 10)
    private String type;

    @Column(name = "TRADE_TYPE", length = 20)
    private String tradeType;

    @Column(name = "ACCOUNT_CODE", length = 20)
    private String codeId;

    @Column(name = "REMARK", length = 200)
    private String remark;

    // 거래 금액 수정
    public void changeAmount(Double amount) {
        this.amount = amount;
    }

    // 거래 유형 변경
    public void changeTransactionType(String type, String tradeType) {
        this.type = type;
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

    // 전체 거래 정보 수정이 필요한 경우를 위한 메서드
    public void updateTransaction(Double amount, String type, String tradeType, String codeId, String remark) {
        this.amount = amount;
        this.type = type;
        this.tradeType = tradeType;
        this.codeId = codeId;
        this.remark = remark;
    }

}