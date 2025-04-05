package com.service.account_manage.repository.nativeQuery;

import com.service.account_manage.api.account_report.vo.data.AccountReportVo;
import com.service.account_manage.api.account_report.vo.request.RequestAccReportVo;
import com.service.core.constants.MessageConstants;
import com.service.core.exception.CustomRuntimeException;
import com.service.core.model.NativeQueryUtilsModel;
import com.service.core.service.MessageService;
import com.service.core.util.ConverterUtils;
import com.service.core.util.NativeQueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.service.core.constants.MessageConstants.*;

@Component
@RequiredArgsConstructor
public class AccountManageNativeQueryStore {

    public static final String FIND_BY_USER_ID_AND_COM_CD_ORDER_BY_ACCOUNT_CODE_COUNT_DESC = "findByUserIdAndComCdOrderByAccountCodeCountDesc";
    public static final String FIND_BY_USER_ID_AND_COM_CD = "findByUserIdAndComCd";
    public static final String FIND_BY_USER_IDS = "findByUserIds";

    private final MessageService messageService;

    private final NativeQueryUtilsModel nativeQueryUtils;

    public List<AccountReportVo> getQueryResult(String queryId, RequestAccReportVo requestAccReportVo, String langCode){
        String query = getQueryString(queryId, requestAccReportVo, langCode);
        return nativeQueryUtils.getQueryResultListByQueryString(query, AccountReportVo.class);
    }

    public List<AccountReportVo> getQueryResult(String queryId, RequestAccReportVo requestAccReportVo){
        return getQueryResult(queryId, requestAccReportVo, null);
    }

    private String getQueryString(String queryId, RequestAccReportVo requestAccReportVo){
        return getQueryString(queryId, requestAccReportVo, null);
    }

    private String getQueryString(String queryId, RequestAccReportVo requestAccReportVo, String langCode){
        String userId = requestAccReportVo.getRequestId();
        String comCd = requestAccReportVo.getRequestComCd();
        String startDate = requestAccReportVo.getStartDate();
        String endDate = requestAccReportVo.getEndDate();
        List<String> userIds = requestAccReportVo.getUserIds();
        LocalDateTime updateLocalDateTime = requestAccReportVo.getUpdateDate();
        String updateDate = ConverterUtils.getDateTimeStringByFormat(updateLocalDateTime, "yyyy-MM-dd HH:mm:ss");

        switch (queryId){
            case FIND_BY_USER_IDS -> {
                return findByUserIds(updateDate, startDate, endDate, comCd, userIds);
            }
            case FIND_BY_USER_ID_AND_COM_CD_ORDER_BY_ACCOUNT_CODE_COUNT_DESC -> {
                return findByUserIdAndComCdOrderByAccountCodeCountDesc(comCd, userId, userIds, startDate, endDate);
            }
            case FIND_BY_USER_ID_AND_COM_CD -> {
                return findByUserIdAndComCd(updateDate, startDate, endDate, comCd, userId);
            }
            default -> {
                throw new CustomRuntimeException(messageService.getMessage(
                        langCode==null ? KO : langCode,null, SEARCH_FAIL
                ));
            }
        }
    }

    private String findByUserIdAndComCdOrderByAccountCodeCountDesc(String comCd, String userId, List<String> userIds, String startDate, String endDate){
        return  " SELECT \n\r" +
                " * \n\r" +
                " FROM \n\r" +
                " ( SELECT \n\r" +
                " IEL.*, \n\r" +
                " ROW_NUMBER() OVER (PARTITION BY IEL.ACCOUNT_CODE ORDER BY IEL.TRANSACTION_DATE DESC) as RN \n\r" +
                " FROM \n\r" +
                " INCOME_EXPENDITURE_LIST IEL \n\r" +
                " LEFT JOIN \n\r" +
                " ( SELECT \n\r" +
                " ACCOUNT_CODE \n\r" +
                " FROM INCOME_EXPENDITURE_LIST \n\r" +
                " WHERE 1=1 \n\r" +
                eqComCd(comCd) +
                eqUserId(userId) +
                inUserIds(userIds) +
                betweenDate(startDate, endDate) +
                " GROUP BY ACCOUNT_CODE \n\r" +
                " ORDER BY COUNT(ACCOUNT_CODE) DESC \n\r" +
                " LIMIT 3 ) as MSTCD \n\r" +
                " ON MSTCD.ACCOUNT_CODE = IEL.ACCOUNT_CODE \n\r" +
                " LEFT JOIN ACCOUNT_CODE_DETAIL ACD \n\r" +
                " ON ACD.COM_CD = IEL.COM_CD \n\r" +
                " AND ACD.CODE_ID = IEL.ACCOUNT_CODE \n\r" +
                " WHERE 1=1 \n\r" +
                eqComCd(comCd) +
                eqUserId(userId) +
                inUserIds(userIds) +
                betweenDate(startDate, endDate) + " ) AS RNT \n\r" +
                " WHERE 1=1 \n\r" +
                " RNT.RN <= 3 \n\r";

    }

    private String findByUserIdAndComCd(String updateDate, String startDate, String endDate, String userId, String comCd){
        return "SELECT \n\r" +
                "* \n\r" +
                "FROM INCOME_EXPENDITURE_LIST \n\r" +
                "WHERE 1=1\n\r" +
                eqUpdateDate(updateDate)
                + betweenDate(startDate, endDate)
                + eqUserId(userId)
                + eqComCd(comCd);
    }

    private String findByUserIds(String updateDate, String startDate, String endDate, String comCd, List<String> userIds){


        return "SELECT \n\r" +
                "* \n\r" +
                "FROM ACCOUNT_MANAGE \n\r" +
                "WHERE 1=1\n\r" +
                eqUpdateDate(updateDate)
                + betweenDate(startDate, endDate)
                + inUserIds(userIds)
                + eqComCd(comCd);
    }


    private String eqComCd(String comCd) {
        if(StringUtils.hasText(comCd)){
            return " AND COM_CD = '" + comCd + "'\n\r";
        }
        return "";
    }

    private String inUserIds(List<String> userIds){
        if(userIds!=null){
            return " AND USER_ID IN ('" + String.join("','", userIds) + "')\n\r";
        }
        return "";
    }

    private String eqUserId(String userId) {
        if(StringUtils.hasText(userId)){
            return " AND USER_ID = '" + userId + "'\n\r";
        }
        return "";
    }

    public String eqUpdateDate(String updateDate) {
        if(StringUtils.hasText(updateDate)){
            return " AND UPDATE_DATE = '" + updateDate + "'\n\r";
        }
        return "";
    }

    public String betweenDate(String startDate, String endDate) {
        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)){
            return " AND TRANSACTION_DATE BETWEEN '" + startDate + "' AND '" + endDate + "'\n\r";
        }
        return "";
    }

}
