package com.service.account_manage.api.accoun_code.service.impl;

import com.service.account_manage.api.accoun_code.service.AccountCodeService;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeVo;
import com.service.account_manage.entity.AccountCodeDetail;
import com.service.account_manage.entity.id.AccountCodeDetailId;
import com.service.account_manage.repository.springJpa.AccountCodeDetailRepository;
import com.service.account_manage.repository.springJpa.AccountCodeRepository;
import com.service.account_manage.repository.springJpa.AccountManagerRepository;
import com.service.core.vo.response.CmnResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCodeServiceImplV1 implements AccountCodeService {

    private final AccountCodeRepository accountCodeRepository;
    private final AccountCodeDetailRepository accountCodeDetailRepository;
    private final AccountManagerRepository accountManagerRepository;

    @Override
    public ResponseEntity<CmnResponseVo> insertAccountCode(AccountCodeVo accountCodeVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> updateAccountCode(AccountCodeVo accountCodeVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> deleteAccountCode(AccountCodeVo accountCodeVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectAccountCode(AccountCodeVo accountCodeVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> insertAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> updateAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> deleteAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectAccountCodeDetail(AccountCodeDetailVo accountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> moveAccountCodeDetailTree(AccountCodeDetailVo accountCodeDetailVo) {
        return null;
    }

    @Override
    public Boolean isAvailableDelete(AccountCodeVo accountCodeVo) {
        List<String> codeIds =
                accountCodeDetailRepository.findByCodeGroupAndUserId(accountCodeVo.getCodeId(), accountCodeVo.getUserId())
                        .stream().map(AccountCodeDetail::getCodeId).toList();
        return !accountManagerRepository.existsByAccountCodesAndUserId(codeIds, accountCodeVo.getUserId());
    }

    @Override
    public Boolean isAvailableDelete(AccountCodeDetailVo accountCodeVo) {
        return !accountManagerRepository.existsByAccountCodeAndUserId(accountCodeVo.getCodeId(), accountCodeVo.getUserId());
    }
}
