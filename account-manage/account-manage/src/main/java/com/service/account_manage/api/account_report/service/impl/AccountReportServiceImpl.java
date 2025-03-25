package com.service.account_manage.api.account_report.service.impl;

import com.service.account_manage.api.account_report.service.AccountReportService;
import com.service.account_manage.api.account_report.vo.data.AccountReportVo;
import com.service.account_manage.api.account_report.vo.request.RequestAccReportVo;
import com.service.account_manage.api.account_report.vo.response.ResponseAccReportVo;
import com.service.account_manage.entity.AccountManager;
import com.service.account_manage.repository.querydsl.AccountManageQueryDsl;
import com.service.account_manage.repository.springJpa.AccountManagerRepository;
import com.service.core.constants.CommonConstants;
import com.service.core.constants.CrudFlag;
import com.service.core.service.ResponseService;
import com.service.core.util.ConverterUtils;
import com.service.core.vo.response.CmnResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static com.service.core.constants.CommonConstants.*;
import static com.service.core.constants.CommonConstants.CREATE;
import static com.service.core.constants.CommonConstants.DELETE;
import static com.service.core.constants.CommonConstants.UPDATE;
import static com.service.core.constants.CrudFlag.*;

@RequiredArgsConstructor
@Service
public class AccountReportServiceImpl implements AccountReportService {

    private final AccountManagerRepository accountManagerRepository;
    private final ResponseService responseService;
    private final AccountManageQueryDsl accountManageQueryDsl;


    @Override
    public ResponseEntity<CmnResponseVo> selectAccountReportList(RequestAccReportVo requestAccReportVo) {
        ResponseAccReportVo responseAccReportVo = new ResponseAccReportVo();
        List<AccountReportVo> accountReportVos = accountManageQueryDsl.findAccountReportAll(requestAccReportVo);
        responseAccReportVo.setDataList(accountReportVos);
        responseAccReportVo.setCmnResponse(responseService.getSearchSuccess());
        return ResponseEntity.ok(responseAccReportVo);
    }

    @Override
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
        String accTransactionId = makeTransactionId(timeKey, userId, comCd);

        List<AccountManager> saveList = new LinkedList<>();
        for(AccountReportVo accountReportVo : executeList){
            accountReportVo.setTransactionDate(transactionDate);
            accountReportVo.setTransactionId(accTransactionId);
            accountReportVo.setUserId(userId);
            accountReportVo.setComCd(comCd);
            accountReportVo.setUpdateUser(userId);
            accountReportVo.setTransactionTimekey(
                    ConverterUtils.getTimeKeyMillSecond(LocalDateTime.now())
            );
            saveList.add(accountReportVo.toEntity());
        }

        accountManagerRepository.saveAll(saveList);
    }

    @Override
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
    public ResponseEntity<CmnResponseVo> deleteAccountReportList(RequestAccReportVo requestAccReportVo) {
        ResponseAccReportVo responseAccReportVo = new ResponseAccReportVo();

        deleteExecute(requestAccReportVo, requestAccReportVo.getExecuteList());
        responseAccReportVo.setCmnResponse(responseService.getModifySuccess());
        return ResponseEntity.ok(responseAccReportVo);
    }

    @Override
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
        return null;
    }

    @Override
    public String makeTransactionId(String timeKey, String userId, String comCd) {
        return comCd + "_" + userId + "_" + timeKey;
    }
}
