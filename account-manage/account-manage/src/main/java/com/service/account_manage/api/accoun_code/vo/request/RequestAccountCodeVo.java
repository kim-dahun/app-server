package com.service.account_manage.api.accoun_code.vo.request;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeVo;
import com.service.core.vo.request.CmnRequestVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
public class RequestAccountCodeVo extends CmnRequestVo {

    List<AccountCodeVo> executeList;

    private String codeId;
    private String codeDesc;

}
