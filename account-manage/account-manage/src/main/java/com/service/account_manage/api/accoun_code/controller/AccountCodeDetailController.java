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
@RequestMapping(ApiConstants.API_BASE+ApiConstants.API_ACCOUNT_MANAGE+"/code-detail")
public class AccountCodeDetailController {

    private final AccountCodeService accountCodeService;
    @GetMapping("/v1")
    public ResponseEntity<CmnResponseVo> api_getAccountCodeDetail(RequestAccountCodeDetailVo requestAccountCodeVo){
        return accountCodeService.selectAccountCodeDetail(requestAccountCodeVo);
    }

    @GetMapping("/v1/tree")
    public ResponseEntity<CmnResponseVo> api_getAccountCodeTree(RequestAccountCodeDetailVo requestAccountCodeVo){
        return accountCodeService.selectAccountCodeDetailTree(requestAccountCodeVo);
    }

    @PostMapping("/v1")
    public ResponseEntity<CmnResponseVo> api_createData(RequestAccountCodeDetailVo requestAccountCodeVo){
        return accountCodeService.insertAccountCodeDetail(requestAccountCodeVo);
    }

    @PutMapping("/v1")
    public ResponseEntity<CmnResponseVo> api_updateData(RequestAccountCodeDetailVo requestAccountCodeVo){
        return accountCodeService.updateAccountCodeDetail(requestAccountCodeVo);
    }

    @PutMapping("/v1/delete")
    public ResponseEntity<CmnResponseVo> api_deleteData(RequestAccountCodeDetailVo requestAccountCodeVo){
        return accountCodeService.deleteAccountCodeDetail(requestAccountCodeVo);
    }

    @PutMapping("/v1/node-change")
    public ResponseEntity<CmnResponseVo> api_moveCodeDetail(RequestAccountCodeDetailVo requestAccountCodeDetailVo){
        return accountCodeService.moveAccountCodeDetailTree(requestAccountCodeDetailVo);
    }

}
