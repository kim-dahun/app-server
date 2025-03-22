package com.service.account_manage.api.accoun_code.vo.request;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
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
public class RequestAccountCodeDetailVo extends CmnRequestVo {

    List<AccountCodeDetailVo> executeList;

    private String upperCodeId; // 상위 코드 검색 기준
    private String codeId; // 코드 검색 기준
    private String codeGroup; // 코드그룹(대분류 검색기준)
    private Integer level;
    private String codeDesc;

}
