package com.service.account_manage.api.accoun_code.service;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeVo;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeVo;
import com.service.core.vo.response.CmnResponseVo;
import org.springframework.http.ResponseEntity;

public interface AccountCodeService {

    ResponseEntity<CmnResponseVo> insertAccountCode(RequestAccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> updateAccountCode(RequestAccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> deleteAccountCode(RequestAccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> selectAccountCode(RequestAccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> insertAccountCodeDetail(RequestAccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> updateAccountCodeDetail(RequestAccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> deleteAccountCodeDetail(RequestAccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> selectAccountCodeDetail(RequestAccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> moveAccountCodeDetailTree(RequestAccountCodeDetailVo accountCodeDetailVo);

    Boolean isAvailableDelete(AccountCodeVo accountCodeVo);

    Boolean isAvailableDelete(AccountCodeDetailVo accountCodeVo);

    ResponseEntity<CmnResponseVo> selectAccountCodeDetailTree(RequestAccountCodeDetailVo accountCodeDetailVo);

}
