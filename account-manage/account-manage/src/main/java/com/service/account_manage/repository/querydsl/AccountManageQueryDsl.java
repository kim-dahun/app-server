package com.service.account_manage.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.account_manage.api.account_report.vo.data.AccountReportVo;
import com.service.account_manage.api.account_report.vo.request.RequestAccReportVo;
import com.service.account_manage.entity.QAccountCodeDetail;
import com.service.account_manage.entity.QAccountManager;
import com.service.core.util.ConverterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static com.service.account_manage.entity.QAccountCodeDetail.accountCodeDetail;
import static com.service.account_manage.entity.QAccountManager.accountManager;

@Service
@RequiredArgsConstructor
public class AccountManageQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    public List<AccountReportVo> findFrequentlyReportList(RequestAccReportVo requestAccReportVo){

        List<AccountReportVo> frequentlyList = new LinkedList<>();

        String comCd = requestAccReportVo.getRequestComCd();
        String userId = requestAccReportVo.getRequestId();
        String codeId = requestAccReportVo.getCodeId();
        String codeDesc = requestAccReportVo.getCodeDesc();
        String useYn = requestAccReportVo.getUseYn();
        String startDate = requestAccReportVo.getStartDate();
        String endDate = requestAccReportVo.getEndDate();
        String transactionId = requestAccReportVo.getTransactionId();

        BooleanBuilder whereCondition = getWhereCondition(comCd, userId, codeId, codeDesc, useYn, startDate, endDate, transactionId);
        BooleanBuilder accountCodeDetailJoin = getAccountCodeDetailJoin();

        List<AccountReportVo> mostCodeList = jpaQueryFactory
                .select(Projections.bean(
                        AccountReportVo.class,
                        accountManager.comCd,
                        accountManager.codeId,
                        accountManager.tradeTarget,
                        accountManager.codeId.count()
                )).from(accountManager)
                .leftJoin(accountCodeDetail)
                .on(accountCodeDetailJoin)
                .where(whereCondition)
                .groupBy(accountManager.comCd, accountManager.codeId, accountManager.tradeTarget)
                .orderBy(accountManager.codeId.count().desc())
                .fetch();

        int limit = Math.min(mostCodeList.size(), 5);
        for(int i = 0 ; i<limit ; i++){
            AccountReportVo accountReportVo = mostCodeList.get(i);
            requestAccReportVo.setTradeTarget(accountReportVo.getTradeTarget());
            requestAccReportVo.setCodeId(accountReportVo.getCodeId());
            requestAccReportVo.setPageNum(0);
            requestAccReportVo.setPageSize(1);
            frequentlyList.addAll(findAccountReportAll(requestAccReportVo));
        }

        return frequentlyList;
    }

    public List<AccountReportVo> findAccountReportAll(RequestAccReportVo requestAccReportVo){

        String comCd = requestAccReportVo.getRequestComCd();
        String userId = requestAccReportVo.getRequestId();
        String codeId = requestAccReportVo.getCodeId();
        String codeDesc = requestAccReportVo.getCodeDesc();
        String useYn = requestAccReportVo.getUseYn();
        String startDate = requestAccReportVo.getStartDate();
        String endDate = requestAccReportVo.getEndDate();
        String transactionId = requestAccReportVo.getTransactionId();

        Integer pageNum = requestAccReportVo.getPageNum();
        Integer pageSize = requestAccReportVo.getPageSize();
        int pageStart = isNotNull(pageNum) && isNotNull(pageSize) ? pageNum * pageSize : 0;
        int pageEnd = isNotNull(pageNum) && isNotNull(pageSize) ? pageNum * pageSize + pageSize : Integer.MAX_VALUE;

        BooleanBuilder whereCondition = getWhereCondition(comCd, userId, codeId, codeDesc, useYn, startDate, endDate, transactionId);
        BooleanBuilder accountCodeDetailJoin = getAccountCodeDetailJoin();

        return jpaQueryFactory
                .select(Projections.bean(
                        AccountReportVo.class,
                        accountManager.comCd,
                        accountManager.userId,
                        accountManager.transactionId,
                        accountManager.transactionDate,
                        accountManager.transactionTimekey,
                        accountManager.useYn,
                        accountManager.amount,
                        accountManager.codeId,
                        accountManager.projectCode,
                        accountManager.tradeTarget,
                        accountManager.tradeTargetDesc,
                        accountManager.tradeType,
                        accountManager.createDate,
                        accountManager.updateDate,
                        accountManager.deleteDate
                )).from(accountManager)
                .leftJoin(accountCodeDetail)
                .on(accountCodeDetailJoin)
                .where(whereCondition)
                .offset(pageStart)
                .limit(pageEnd)
                .orderBy(accountManager.transactionId.desc(), accountManager.transactionTimekey.asc())
                .fetch();

    }

    private BooleanBuilder getAccountCodeDetailJoin() {
        return new BooleanBuilder()
                .and(accountManager.codeId.eq(accountCodeDetail.codeId))
                .and(accountManager.comCd.eq(accountCodeDetail.comCd));
    }

    private BooleanBuilder getWhereCondition(String comCd, String userId, String codeId, String codeDesc, String useYn, String startDate, String endDate, String transactionId) {
        return new BooleanBuilder()
                .and(eqComCd(comCd))
                .and(eqUserId(userId))
                .and(eqCodeId(codeId))
                .and(likeCodeDesc(codeDesc))
                .and(eqUseYn(useYn))
                .and(betweenDate(startDate,endDate))
                .and(eqTransactionId(transactionId));
    }

    private BooleanExpression eqTransactionId(String transactionId) {
        if(isNotNull(transactionId)){
            return accountManager.transactionId.eq(transactionId);
        }
        return null;
    }

    private BooleanExpression betweenDate(String startDate, String endDate) {
        if(isNotNull(startDate) && isNotNull(endDate)){
            return accountManager.transactionDate.between(startDate,endDate);
        }
        return null;
    }

    private BooleanExpression eqUseYn(String useYn) {
        if(isNotNull(useYn)){
            return accountManager.useYn.eq(useYn);
        }
        return null;
    }

    private BooleanExpression likeCodeDesc(String codeDesc) {
        if(isNotNull(codeDesc)){
            return accountCodeDetail.codeDesc.likeIgnoreCase("%"+codeDesc+"%");
        }
        return null;
    }

    private BooleanExpression eqCodeId(String codeId) {
        if(isNotNull(codeId)){
            return accountManager.codeId.eq(codeId);
        }
        return null;
    }

    private BooleanExpression eqUserId(String userId) {
        if(isNotNull(userId)){
            return accountManager.userId.eq(userId);
        }
        return null;
    }

    private BooleanExpression eqComCd(String comCd) {
        if (isNotNull(comCd)){
            return accountManager.comCd.eq(comCd);
        }
        return null;
    }

    private boolean isNotNull(Object obj){
        return obj!=null;
    }

}
