package com.service.account_manage.api.account_report.service.impl;

import com.service.account_manage.api.account_report.service.AccountReportService;
import com.service.account_manage.api.account_report.vo.data.AccountReportVo;
import com.service.account_manage.api.account_report.vo.request.RequestAccReportVo;
import com.service.account_manage.api.account_report.vo.response.ResponseAccReportVo;
import com.service.account_manage.entity.AccountManager;
import com.service.account_manage.repository.nativeQuery.AccountManageNativeQueryStore;
import com.service.account_manage.repository.querydsl.AccountManageQueryDsl;
import com.service.account_manage.repository.springJpa.AccountManagerRepository;
import com.service.core.service.ResponseService;
import com.service.core.util.ConverterUtils;
import com.service.core.util.ObjectUtils;
import com.service.core.vo.response.CmnResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static com.service.core.constants.CommonConstants.CREATE;
import static com.service.core.constants.CommonConstants.DELETE;
import static com.service.core.constants.CommonConstants.UPDATE;

@RequiredArgsConstructor
@Service
public class AccountReportServiceImplV1 implements AccountReportService {

    private final AccountManagerRepository accountManagerRepository;
    private final ResponseService responseService;
    private final AccountManageQueryDsl accountManageQueryDsl;
    private final AccountManageNativeQueryStore accountManageNativeQueryStore;


    @Override
    public ResponseEntity<CmnResponseVo> selectAccountReportList(RequestAccReportVo requestAccReportVo) {
        ResponseAccReportVo responseAccReportVo = new ResponseAccReportVo();
        List<AccountReportVo> accountReportVos = accountManageQueryDsl.findAccountReportAll(requestAccReportVo);
        responseAccReportVo.setDataList(accountReportVos);
        responseAccReportVo.setCmnResponse(responseService.getSearchSuccess());
        return ResponseEntity.ok(responseAccReportVo);
    }

    @Override
    @Transactional
    public ResponseEntity<CmnResponseVo> insertAccountReportList(RequestAccReportVo requestAccReportVo) {
        ResponseAccReportVo responseAccReportVo = new ResponseAccReportVo();
        createExecute(requestAccReportVo, requestAccReportVo.getExecuteList());
        responseAccReportVo.setCmnResponse(responseService.getModifySuccess());
        return ResponseEntity.ok(responseAccReportVo);
    }

    private void createExecute(RequestAccReportVo requestAccReportVo, List<AccountReportVo> executeList) {
        LocalDateTime now = LocalDateTime.now();
        String comCd = requestAccReportVo.getRequestComCd();
        String userId = requestAccReportVo.getRequestId();
        String timeKey = ConverterUtils.getTimeKeyMillSecond(now);
        String transactionDate = ConverterUtils.getDateTimeStringByFormat(now,"yyyy-MM-dd");
        String accTransactionId = ObjectUtils.getCommonTableId(userId,comCd,now);

        List<AccountManager> saveList = new LinkedList<>();
        for(AccountReportVo accountReportVo : executeList){
            accountReportVo.setTransactionDate(transactionDate);
            accountReportVo.setTransactionId(accTransactionId);
            accountReportVo.setUserId(userId);
            accountReportVo.setComCd(comCd);
            accountReportVo.setUpdateUser(userId);
            accountReportVo.setTransactionTimekey(timeKey);
            saveList.add(accountReportVo.toEntity());
        }

        accountManagerRepository.saveAll(saveList);
    }

    @Override
    @Transactional
    public ResponseEntity<CmnResponseVo> updateAccountReportList(RequestAccReportVo requestAccReportVo) {
        ResponseAccReportVo responseAccReportVo = new ResponseAccReportVo();
        updateExecute(requestAccReportVo, requestAccReportVo.getExecuteList());
        responseAccReportVo.setCmnResponse(responseService.getModifySuccess());
        return ResponseEntity.ok(responseAccReportVo);
    }


    private void updateExecute(RequestAccReportVo requestAccReportVo, List<AccountReportVo> executeList) {
        List<AccountManager> saveList = new LinkedList<>();
        String userId = requestAccReportVo.getRequestId();
        for(AccountReportVo accountReportVo : executeList){
            accountReportVo.setUpdateUser(userId);
            saveList.add(accountReportVo.toEntity());
        }

        accountManagerRepository.saveAll(saveList);
    }

    @Override
    @Transactional
    public ResponseEntity<CmnResponseVo> deleteAccountReportList(RequestAccReportVo requestAccReportVo) {
        ResponseAccReportVo responseAccReportVo = new ResponseAccReportVo();

        deleteExecute(requestAccReportVo, requestAccReportVo.getExecuteList());
        responseAccReportVo.setCmnResponse(responseService.getModifySuccess());
        return ResponseEntity.ok(responseAccReportVo);
    }

    @Override
    @Transactional
    public ResponseEntity<CmnResponseVo> modifyAccountReportList(RequestAccReportVo requestAccReportVo) {
        ResponseAccReportVo responseAccReportVo = new ResponseAccReportVo();
        List<AccountReportVo> createList = new LinkedList<>();
        List<AccountReportVo> updateList = new LinkedList<>();
        List<AccountReportVo> deleteList = new LinkedList<>();
        for(AccountReportVo accountReportVo : requestAccReportVo.getExecuteList()){
            switch (accountReportVo.getCrudFlag()){
                case CREATE -> {
                    createList.add(accountReportVo);
                }
                case UPDATE -> {
                    updateList.add(accountReportVo);
                }
                case DELETE -> {
                    deleteList.add(accountReportVo);
                }
            }
        }
        createExecute(requestAccReportVo, createList);
        updateExecute(requestAccReportVo, updateList);
        deleteExecute(requestAccReportVo, deleteList);

        responseAccReportVo.setCmnResponse(responseService.getModifySuccess());
        return ResponseEntity.ok(responseAccReportVo);
    }

    private void deleteExecute(RequestAccReportVo requestAccReportVo, List<AccountReportVo> executeList) {
        List<AccountManager> saveList = new LinkedList<>();
        String userId = requestAccReportVo.getRequestId();
        for(AccountReportVo accountReportVo : executeList){
            accountReportVo.setUpdateUser(userId);
            AccountManager accountManager = accountReportVo.toEntity();
            accountManager.delete(userId);
            saveList.add(accountManager);
        }
        accountManagerRepository.deleteAllInBatch(saveList);
    }

    @Override
    public ResponseEntity<CmnResponseVo> selectFrequentlyAccountReportList(RequestAccReportVo requestAccReportVo) {
        List<AccountReportVo> accountReportVos = accountManageNativeQueryStore.getQueryResult(AccountManageNativeQueryStore.FIND_BY_USER_ID_AND_COM_CD_ORDER_BY_ACCOUNT_CODE_COUNT_DESC, requestAccReportVo);
        ResponseAccReportVo cmnResponseVo = new ResponseAccReportVo();
        cmnResponseVo.setCmnResponse(responseService.getSearchSuccess());
        cmnResponseVo.setDataList(accountReportVos);
        return ResponseEntity.ok(cmnResponseVo);
    }


    private String makeTransactionId(String timeKey, String userId, String comCd) {
        return comCd + "_" + userId + "_" + timeKey;
    }
}
