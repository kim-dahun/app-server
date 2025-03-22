package com.service.account_manage.api.accoun_code.vo.request;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;

import java.util.List;

public class RequestAccountCodeDetailVo {

    List<AccountCodeDetailVo> executeList;

    private String upperCodeId; // 상위 코드 검색 기준
    private String codeId; // 코드 검색 기준
    private String codeGroup; // 코드그룹(대분류 검색기준)

}
