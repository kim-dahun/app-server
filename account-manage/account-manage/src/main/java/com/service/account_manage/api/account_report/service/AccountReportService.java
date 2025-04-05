package com.service.account_manage.api.account_report.service;

import com.service.account_manage.api.account_report.vo.request.RequestAccReportVo;
import com.service.core.vo.response.CmnResponseVo;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface AccountReportService {


    ResponseEntity<CmnResponseVo> selectAccountReportList(RequestAccReportVo requestAccReportVo);

    ResponseEntity<CmnResponseVo> insertAccountReportList(RequestAccReportVo requestAccReportVo);

    ResponseEntity<CmnResponseVo> updateAccountReportList(RequestAccReportVo requestAccReportVo);

    ResponseEntity<CmnResponseVo> deleteAccountReportList(RequestAccReportVo requestAccReportVo);

    ResponseEntity<CmnResponseVo> modifyAccountReportList(RequestAccReportVo requestAccReportVo);

    /**
     * 입력된 거래내역을 토대로 최근 7일 기준, 최근 30일 기준, 최근 3개월 기준 가장 많은 거래가 발생한 코드 기준
     * 이전 거래내역 조회 - 바로 불러와서 비고와 금액만 수정 후 저장 가능하도록.
     * @param requestAccReportVo
     * @return ResponseEntity
     */
    ResponseEntity<CmnResponseVo> selectFrequentlyAccountReportList(RequestAccReportVo requestAccReportVo);

}
