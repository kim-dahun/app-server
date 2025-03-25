package com.service.account_manage.api.account_report.vo.response;

import com.service.account_manage.api.account_report.vo.data.AccountReportVo;
import com.service.core.vo.response.CmnResponseVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ResponseAccReportVo extends CmnResponseVo {

    private List<AccountReportVo> dataList;

    private AccountReportVo data;

}
