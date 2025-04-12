package com.service.account_manage.api.account_report.controller;

import com.service.account_manage.api.account_report.service.AccountReportService;
import com.service.account_manage.api.account_report.vo.request.RequestAccReportVo;
import com.service.core.constants.ApiConstants;
import com.service.core.vo.response.CmnResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(ApiConstants.API_BASE+ApiConstants.API_ACCOUNT_MANAGE+"/report")
public class AccountReportController {

    private final AccountReportService accountReportService;

    @GetMapping
    public ResponseEntity<CmnResponseVo> api_getAccountReport(RequestAccReportVo requestAccReportVo){
        return accountReportService.selectAccountReportList(requestAccReportVo);
    }

    @PostMapping
    public ResponseEntity<CmnResponseVo> api_createAccountReport(@RequestBody RequestAccReportVo requestAccReportVo){
        return accountReportService.insertAccountReportList(requestAccReportVo);
    }

    @PutMapping
    public ResponseEntity<CmnResponseVo> api_updateAccountReport(@RequestBody RequestAccReportVo requestAccReportVo){
        return accountReportService.updateAccountReportList(requestAccReportVo);
    }

    @PutMapping("/delete")
    public ResponseEntity<CmnResponseVo> api_deleteAccountReport(@RequestBody RequestAccReportVo requestAccReportVo){
        return accountReportService.deleteAccountReportList(requestAccReportVo);
    }

    @GetMapping("/most-trade")
    public ResponseEntity<CmnResponseVo> api_getFrequentlyTradeList(RequestAccReportVo reportVo){
        return accountReportService.selectFrequentlyAccountReportList(reportVo);
    }

    @PostMapping("/list")
    public ResponseEntity<CmnResponseVo> api_modifyAccountReportList(@RequestBody RequestAccReportVo requestAccReportVo){
        return accountReportService.modifyAccountReportList(requestAccReportVo);
    }
}
