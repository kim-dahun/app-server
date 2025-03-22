package com.service.account_manage.api.account_report.vo.request;

import com.service.account_manage.api.account_report.vo.data.AccountReportVo;
import com.service.core.vo.request.CmnRequestVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class RequestAccReportVo extends CmnRequestVo {

    List<AccountReportVo> executeList;

    private String codeId;
    private String codeDesc;
    private String tradeType;
    private String type;
    private String useYn;

    private String startDate;
    private String endDate;

}
