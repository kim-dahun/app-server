package com.service.account_manage.api.accoun_code.service;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeVo;
import com.service.core.vo.response.CmnResponseVo;
import org.springframework.http.ResponseEntity;

public interface AccountCodeService {

    ResponseEntity<CmnResponseVo> insertAccountCode(AccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> updateAccountCode(AccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> deleteAccountCode(AccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> selectAccountCode(AccountCodeVo accountCodeVo);

    ResponseEntity<CmnResponseVo> insertAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> updateAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> deleteAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> selectAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo);

    ResponseEntity<CmnResponseVo> moveAccountCodeDetailTree(AccountCodeDetailVo accountCodeDetailVo);

    Boolean isAvailableDelete(AccountCodeVo accountCodeVo);

    Boolean isAvailableDelete(AccountCodeDetailVo accountCodeVo);

}
