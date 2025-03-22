package com.service.account_manage.api.accoun_code.controller;


import com.service.account_manage.api.accoun_code.service.AccountCodeService;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeDetailVo;
import com.service.account_manage.api.accoun_code.vo.request.RequestAccountCodeVo;
import com.service.core.constants.ApiConstants;
import com.service.core.vo.response.CmnResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(ApiConstants.API_BASE+ApiConstants.API_ACCOUNT_MANAGE+"/code")
public class AccountCodeController {

    private final AccountCodeService accountCodeService;

    @GetMapping("/v1")
    public ResponseEntity<CmnResponseVo> api_getAccountCodeMaster(RequestAccountCodeVo requestAccountCodeVo){
        return accountCodeService.selectAccountCode(requestAccountCodeVo);
    }

    @PostMapping("/v1")
    public ResponseEntity<CmnResponseVo> api_createData(RequestAccountCodeVo requestAccountCodeVo){
        return accountCodeService.insertAccountCode(requestAccountCodeVo);
    }

    @PutMapping("/v1")
    public ResponseEntity<CmnResponseVo> api_updateData(RequestAccountCodeVo requestAccountCodeVo){
        return accountCodeService.updateAccountCode(requestAccountCodeVo);
    }

    @PutMapping("/v1/delete")
    public ResponseEntity<CmnResponseVo> api_deleteData(RequestAccountCodeVo requestAccountCodeVo){
        return accountCodeService.deleteAccountCode(requestAccountCodeVo);
    }

}
