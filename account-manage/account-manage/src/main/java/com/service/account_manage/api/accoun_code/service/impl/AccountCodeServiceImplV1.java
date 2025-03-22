package com.service.account_manage.api.accoun_code.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.account_manage.api.accoun_code.service.AccountCodeService;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeVo;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeVo;
import com.service.account_manage.api.accoun_code.vo.response.ResponseAccountCode;
import com.service.account_manage.entity.AccountCode;
import com.service.account_manage.entity.AccountCodeDetail;
import com.service.account_manage.entity.AccountManager;
import com.service.account_manage.entity.id.AccountCodeDetailId;
import com.service.account_manage.repository.springJpa.AccountCodeDetailRepository;
import com.service.account_manage.repository.springJpa.AccountCodeRepository;
import com.service.account_manage.repository.springJpa.AccountManagerRepository;
import com.service.core.constants.MessageConstants;
import com.service.core.service.MessageService;
import com.service.core.service.ResponseService;
import com.service.core.util.JpaUtils;
import com.service.core.vo.response.CmnResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.service.core.constants.MessageConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCodeServiceImplV1 implements AccountCodeService {

    private final AccountCodeRepository accountCodeRepository;
    private final AccountCodeDetailRepository accountCodeDetailRepository;
    private final AccountManagerRepository accountManagerRepository;
    private final ResponseService responseService;
    private final MessageService messageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> insertAccountCode(RequestAccountCodeVo requestAccountCodeVo) {
        try {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getCreateUserSuccess());
            List<AccountCode> modifyList= new LinkedList<>();
            for(AccountCodeVo accountCodeVo : requestAccountCodeVo.getExecuteList()){
                modifyList.add(accountCodeVo.toAccountCodeEntity());
            }
            accountCodeRepository.saveAll(modifyList);
            return ResponseEntity.ok(responseAccountCode);
        } catch (Exception e){
            throw new IllegalArgumentException(
                    messageService.getMessage(KO,null,MODIFY_FAIL)
            );
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> updateAccountCode(RequestAccountCodeVo requestAccountCodeVo) {
        try {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getCreateUserSuccess());
            Map<String, AccountCodeVo> entityMap = new HashMap<>();
            List<String> codeIds = new LinkedList<>();
            addElement(requestAccountCodeVo, codeIds, entityMap);
            updateAccountCode(codeIds, entityMap);
            return ResponseEntity.ok(responseAccountCode);
        } catch (Exception e){
            throw new IllegalArgumentException(
                    messageService.getMessage(KO,null,MODIFY_FAIL)
            );
        }
    }

    private void updateAccountCode(List<String> codeIds, Map<String, AccountCodeVo> entityMap) {
        List<AccountCode> modifyList= accountCodeRepository.findByCodeIdIn(codeIds);
        for(AccountCode accountCode : modifyList){
            AccountCodeVo updateDataVo = entityMap.get(accountCode.getCodeId());
            if(updateDataVo!=null){
                accountCode.updateCodeDesc(updateDataVo.getCodeDesc());
            }
        }
        accountCodeRepository.saveAll(modifyList);
    }

    private void addElement(RequestAccountCodeVo requestAccountCodeVo, List<String> codeIds, Map<String, AccountCodeVo> entityMap) {
        for(AccountCodeVo accountCodeVo : requestAccountCodeVo.getExecuteList()){
            String codeId = accountCodeVo.getCodeId();
            codeIds.add(codeId);
            entityMap.put(codeId, accountCodeVo);
        }
    }

    @Override
    public ResponseEntity<CmnResponseVo> deleteAccountCode(RequestAccountCodeVo requestAccountCodeVo) {
        ResponseAccountCode responseAccountCode = new ResponseAccountCode();
        responseAccountCode.setCmnResponse(responseService.getModifySuccess());
        List<AccountCodeVo> executeList = requestAccountCodeVo.getExecuteList();
        for(AccountCodeVo accountCodeVo : executeList){
            if(!isAvailableDelete(accountCodeVo)){
                throw new IllegalArgumentException(messageService.getMessage(KO,null,MODIFY_FAIL));
            }
        }
        return ResponseEntity.ok(responseAccountCode);
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectAccountCode(RequestAccountCodeVo requestAccountCodeVo) {
        try {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getSearchSuccess());

            ExampleMatcher exampleMatcher = ExampleMatcher.matching();
            JpaUtils.equalCondition("codeId",exampleMatcher);
            JpaUtils.equalCondition("userId",exampleMatcher);
            JpaUtils.likeCondition("codeDesc",exampleMatcher);
            JpaUtils.nullValueIgnore(exampleMatcher);
            List<AccountCode> accountCodes = getDataByCodeIdAndUserIdAndCodeDesc(requestAccountCodeVo, exampleMatcher);
            responseAccountCode.setDataList(
                    accountCodes.stream().map(AccountCodeVo::fromAccountCodeEntity).toList()
            );
            return ResponseEntity.ok(responseAccountCode);
        } catch (Exception e){
            throw new IllegalArgumentException(
                    messageService.getMessage(KO,null,SEARCH_FAIL)
            );
        }

    }

    private List<AccountCode> getDataByCodeIdAndUserIdAndCodeDesc(RequestAccountCodeVo requestAccountCodeVo, ExampleMatcher exampleMatcher) {
        return accountCodeRepository.findAll(
                Example.of(
                        AccountCode.builder()
                                .codeId(requestAccountCodeVo.getCodeId())
                                .codeDesc(requestAccountCodeVo.getCodeDesc())
                                .userId(requestAccountCodeVo.getRequestId())
                                .build(), exampleMatcher
                )
        );
    }

    @Override
    public ResponseEntity<CmnResponseVo> insertAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> updateAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> deleteAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
        return null;
    }

    @Override
    public ResponseEntity<CmnResponseVo> moveAccountCodeDetailTree(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
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
