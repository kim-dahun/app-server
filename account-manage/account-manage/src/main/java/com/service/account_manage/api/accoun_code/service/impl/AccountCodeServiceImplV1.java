package com.service.account_manage.api.accoun_code.service.impl;

import com.service.account_manage.api.accoun_code.service.AccountCodeService;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.data.AccountCodeVo;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeVo;
import com.service.account_manage.api.accoun_code.vo.response.ResponseAccountCode;
import com.service.account_manage.api.accoun_code.vo.response.ResponseAccountCodeDetailVo;
import com.service.account_manage.entity.AccountCode;
import com.service.account_manage.entity.AccountCodeDetail;
import com.service.account_manage.entity.id.AccountCodeDetailId;
import com.service.account_manage.repository.springJpa.AccountCodeDetailRepository;
import com.service.account_manage.repository.springJpa.AccountCodeRepository;
import com.service.account_manage.repository.springJpa.AccountManagerRepository;
import com.service.core.exception.CustomRuntimeException;
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
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getModifySuccess());
            List<AccountCode> modifyList= new LinkedList<>();
            for(AccountCodeVo accountCodeVo : requestAccountCodeVo.getExecuteList()){
                modifyList.add(accountCodeVo.toAccountCodeEntity());
            }
            accountCodeRepository.saveAll(modifyList);
            return ResponseEntity.ok(responseAccountCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> updateAccountCode(RequestAccountCodeVo requestAccountCodeVo) {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getModifySuccess());
            Map<String, AccountCodeVo> entityMap = new HashMap<>();
            List<String> codeIds = new LinkedList<>();
            String userId = requestAccountCodeVo.getRequestId();
            addElement(requestAccountCodeVo, codeIds, entityMap);
            updateAccountCode(userId, codeIds, entityMap);
            return ResponseEntity.ok(responseAccountCode);
    }

    private void updateAccountCode(String userId, List<String> codeIds, Map<String, AccountCodeVo> entityMap) {
        List<AccountCode> modifyList= accountCodeRepository.findByCodeIdInAndUserId(codeIds, userId);
        for(AccountCode accountCode : modifyList){
            AccountCodeVo updateDataVo = entityMap.get(accountCode.getCodeId());
            if(updateDataVo!=null){
                accountCode.updateCodeDesc(updateDataVo.getCodeDesc());
            }
        }
        accountCodeRepository.saveAll(modifyList);
    }

    private void updateAccountCodeDetail(String userId, Collection<String> codeGroups, Collection<String> codeIds, Map<String, AccountCodeDetailVo> entityMap) {
        List<AccountCodeDetail> modifyList= accountCodeDetailRepository.findByCodeIdInAndUserIdAndCodeGroupIn(codeIds, userId, codeGroups);
        for(AccountCodeDetail accountCodeDetail : modifyList){
            AccountCodeDetailVo updateDataVo = entityMap.get(accountCodeDetail.getCodeId());
            if(updateDataVo!=null){
                if(updateDataVo.getParentCode()!=null && updateDataVo.getLevel()!=null){
                    accountCodeDetail.updateParentCode(updateDataVo.getParentCode(), updateDataVo.getLevel());
                }
                accountCodeDetail.updateCodeDesc(updateDataVo.getCodeDesc());
            }
        }
        accountCodeDetailRepository.saveAll(modifyList);
    }

    private void addElement(RequestAccountCodeDetailVo requestAccountCodeVo, Collection<String> codeGroups, Collection<String> codeIds, Map<String, AccountCodeDetailVo> entityMap) {
        for(AccountCodeDetailVo accountCodeVo : requestAccountCodeVo.getExecuteList()){
            String codeId = accountCodeVo.getCodeId();
            String codeGroup = accountCodeVo.getCodeGroup();
            codeIds.add(codeId);
            codeGroups.add(codeGroup);
            entityMap.put(codeId, accountCodeVo);
        }
    }

    private void addElement(RequestAccountCodeVo requestAccountCodeVo, Collection<String> codeIds, Map<String, AccountCodeVo> entityMap) {
        for(AccountCodeVo accountCodeVo : requestAccountCodeVo.getExecuteList()){
            String codeId = accountCodeVo.getCodeId();
            codeIds.add(codeId);
            entityMap.put(codeId, accountCodeVo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> deleteAccountCode(RequestAccountCodeVo requestAccountCodeVo) {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getModifySuccess());
            List<AccountCodeVo> executeList = requestAccountCodeVo.getExecuteList();
            for (AccountCodeVo accountCodeVo : executeList) {
                if (!isAvailableDelete(accountCodeVo)) {
                    throw new CustomRuntimeException(messageService.getMessage(KO,null,DELETE_UNAVAILABLE));
                }
            }
            accountCodeRepository.deleteAllInBatch(executeList.stream().map(AccountCodeVo::toAccountCodeEntity).toList());
            return ResponseEntity.ok(responseAccountCode);
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectAccountCode(RequestAccountCodeVo requestAccountCodeVo) {
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
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> insertAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getCreateUserSuccess());
            List<AccountCodeDetail> modifyList= new LinkedList<>();
            for(AccountCodeDetailVo accountCodeDetailVo : requestAccountCodeDetailVo.getExecuteList()){
                modifyList.add(accountCodeDetailVo.toAccountCodeDetailEntity());
            }
            accountCodeDetailRepository.saveAll(modifyList);
            return ResponseEntity.ok(responseAccountCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> updateAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getCreateUserSuccess());
            Map<String, AccountCodeDetailVo> entityMap = new HashMap<>();
            List<String> codeIds = new LinkedList<>();
            Set<String> codeGroups = new HashSet<>();
            String userId = requestAccountCodeDetailVo.getRequestId();
            addElement(requestAccountCodeDetailVo, codeIds, codeGroups, entityMap);
            updateAccountCodeDetail(userId,codeGroups, codeIds, entityMap);
            return ResponseEntity.ok(responseAccountCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> deleteAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
            ResponseAccountCode responseAccountCode = new ResponseAccountCode();
            responseAccountCode.setCmnResponse(responseService.getModifySuccess());
            List<AccountCodeDetailVo> executeList = requestAccountCodeDetailVo.getExecuteList();
            for (AccountCodeDetailVo accountCodeDetailVo : executeList) {
                if (!isAvailableDelete(accountCodeDetailVo)) {
                    throw new CustomRuntimeException(messageService.getMessage(KO,null,DELETE_UNAVAILABLE));
                }
            }
            accountCodeDetailRepository.deleteAllInBatch(executeList.stream().map(AccountCodeDetailVo::toAccountCodeDetailEntity).toList());
            return ResponseEntity.ok(responseAccountCode);
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {
            ResponseAccountCodeDetailVo responseAccountCode = new ResponseAccountCodeDetailVo();
            responseAccountCode.setCmnResponse(responseService.getSearchSuccess());

            ExampleMatcher exampleMatcher = ExampleMatcher.matching();
            JpaUtils.equalCondition("codeId",exampleMatcher);
            JpaUtils.equalCondition("userId",exampleMatcher);
            JpaUtils.likeCondition("codeDesc",exampleMatcher);
            JpaUtils.equalCondition("level",exampleMatcher);
            JpaUtils.equalCondition("parentCode",exampleMatcher);
            JpaUtils.nullValueIgnore(exampleMatcher);
            List<AccountCodeDetail> accountCodeDetails = getDetailDataByCodeIdAndUserIdAndCodeDesc(requestAccountCodeDetailVo, exampleMatcher);
            responseAccountCode.setDataList(
                    accountCodeDetails.stream().map(AccountCodeDetailVo::fromAccountCodeDetailEntity).toList()
            );
            return ResponseEntity.ok(responseAccountCode);
    }

    private List<AccountCodeDetail> getDetailDataByCodeIdAndUserIdAndCodeDesc(RequestAccountCodeDetailVo requestAccountCodeDetailVo, ExampleMatcher exampleMatcher) {
        return accountCodeDetailRepository.findAll(
                Example.of(
                        AccountCodeDetail.builder()
                                .codeId(requestAccountCodeDetailVo.getCodeId())
                                .level(requestAccountCodeDetailVo.getLevel())
                                .userId(requestAccountCodeDetailVo.getRequestId())
                                .codeDesc(requestAccountCodeDetailVo.getCodeDesc())
                                .parentCode(requestAccountCodeDetailVo.getUpperCodeId())
                                .build(), exampleMatcher
                )
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CmnResponseVo> moveAccountCodeDetailTree(RequestAccountCodeDetailVo requestAccountCodeDetailVo) {

            ResponseAccountCodeDetailVo responseAccountCode = new ResponseAccountCodeDetailVo();
            responseAccountCode.setCmnResponse(responseService.getModifySuccess());
            String userId = requestAccountCodeDetailVo.getRequestId();
            String codeId= requestAccountCodeDetailVo.getCodeId();
            String codeGroup = requestAccountCodeDetailVo.getCodeGroup();
            String parentCode = requestAccountCodeDetailVo.getUpperCodeId();
            Integer level = requestAccountCodeDetailVo.getLevel();

            AccountCodeDetail accountCodeDetail = accountCodeDetailRepository.findById(
                    AccountCodeDetailId.builder()
                            .userId(userId)
                            .codeId(codeId)
                            .codeGroup(codeGroup)
                            .build()
            ).orElse(null);
            if (accountCodeDetail == null){
                throw new CustomRuntimeException(messageService.getMessage(KO,null,MODIFY_FAIL));
            }
            accountCodeDetail.updateParentCode(parentCode, level);
            accountCodeDetailRepository.save(accountCodeDetail);
            return ResponseEntity.ok(responseAccountCode);
    }

    @Override
    public Boolean isAvailableDelete(AccountCodeVo accountCodeVo) {
        List<String> codeIds =
                accountCodeDetailRepository.findByCodeGroupAndUserId(accountCodeVo.getCodeId(), accountCodeVo.getUserId())
                        .stream().map(AccountCodeDetail::getCodeId).toList();
        return !accountManagerRepository.existsByCodeIdInAndUserId(codeIds, accountCodeVo.getUserId());
    }

    @Override
    public Boolean isAvailableDelete(AccountCodeDetailVo accountCodeVo) {
        return !accountManagerRepository.existsByCodeIdAndUserId(accountCodeVo.getCodeId(), accountCodeVo.getUserId());
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectAccountCodeDetailTree(RequestAccountCodeDetailVo accountCodeDetailVo) {
        ResponseAccountCodeDetailVo responseAccountCode = new ResponseAccountCodeDetailVo();
        responseAccountCode.setCmnResponse(responseService.getSearchSuccess());

        List<AccountCodeDetailVo> nodeList = getNodeList(accountCodeDetailVo);
        List<AccountCodeDetailVo> childList = getChildList(accountCodeDetailVo);
        for(AccountCodeDetailVo node : nodeList){
            node.setChildList(getTreeList(node, childList));
        }
        responseAccountCode.setDataList(nodeList);
        return ResponseEntity.ok(responseAccountCode);
    }

    private List<AccountCodeDetailVo> getChildList(RequestAccountCodeDetailVo accountCodeDetailVo) {
        return accountCodeDetailRepository.findAll(
                Example.of(
                        AccountCodeDetail
                                .builder()
                                .codeGroup(accountCodeDetailVo.getCodeGroup())
                                .userId(accountCodeDetailVo.getRequestId())
                                .build(),
                        ExampleMatcher.matching()
                                .withIgnoreNullValues()
                )
        ).stream().map(AccountCodeDetailVo::fromAccountCodeDetailEntity).toList();
    }

    private List<AccountCodeDetailVo> getNodeList(RequestAccountCodeDetailVo accountCodeDetailVo) {
        List<AccountCode> accountCodes = accountCodeRepository.findAll(
                Example.of(
                        AccountCode
                                .builder()
                                .userId(accountCodeDetailVo.getRequestId())
                                .codeId(accountCodeDetailVo.getCodeGroup())
                                .build(),
                        ExampleMatcher.matching()
                                .withIgnoreNullValues()
                )
        );

        return accountCodes.stream().map(accountCode-> {
                    AccountCodeDetailVo newVoData = AccountCodeDetailVo
                            .builder()
                            .parentCode(null)
                            .codeGroup(accountCode.getCodeId())
                            .level(0)
                            .build();
                    return newVoData;
                }
        ).toList();
    }

    private List<AccountCodeDetailVo> getTreeList(AccountCodeDetailVo node, List<AccountCodeDetailVo> accountCodeDetailVos) {
        List<AccountCodeDetailVo> childList = new LinkedList<>();
        List<AccountCodeDetailVo> filterList = getFilterList(node, accountCodeDetailVos);
        for(AccountCodeDetailVo accountCodeDetailVo : accountCodeDetailVos){
            if(isChild(node.getCodeId(), node.getLevel(), node.getCodeGroup(), accountCodeDetailVo.getCodeGroup(), accountCodeDetailVo.getCodeId(), accountCodeDetailVo.getLevel())){
                accountCodeDetailVo.setChildList(getTreeList(accountCodeDetailVo, filterList));
                childList.add(accountCodeDetailVo);
            }
        }
        return childList;
    }

    private List<AccountCodeDetailVo> getFilterList(AccountCodeDetailVo node, List<AccountCodeDetailVo> accountCodeDetailVos) {
        return accountCodeDetailVos.stream().filter(accountCodeDetailVo -> (isEqualValue(accountCodeDetailVo.getParentCode(), node.getCodeId()) && isEqualValue(node.getCodeGroup(), accountCodeDetailVo.getCodeGroup()) && isEqualValue(accountCodeDetailVo.getLevel()-1,node.getLevel()))).toList();
    }

    private boolean isChild(String upperCode, int upperLevel, String codeGroup, String upperCodeGroup, String code, int level){
        return caseUpperLevelZero(upperLevel, codeGroup, upperCodeGroup) || caseUpperLevelOverZero(codeGroup, upperCode, upperCodeGroup ,upperLevel, code, level);
    }

    private boolean caseUpperLevelOverZero(String codeGroup, String upperCode, String upperCodeGroup, int upperLevel, String code, int level) {
        return isEqualValue(codeGroup, upperCodeGroup) && upperLevel > 0 && isEqualValue(upperCode, code) && isEqualValue(level-1, upperLevel);
    }

    private boolean isEqualValue(Object item, Object upperItem) {
        return item.equals(upperItem);
    }

    private boolean caseUpperLevelZero(int upperLevel, String codeGroup, String upperCodeGroup) {
        return isEqualValue(0,upperLevel) && isEqualValue(codeGroup, upperCodeGroup);
    }

}
