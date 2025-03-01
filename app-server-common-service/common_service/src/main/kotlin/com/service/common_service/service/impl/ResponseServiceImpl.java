package com.service.common_service.service.impl;

import com.service.common_service.constants.HttpStatusConstants;
import com.service.common_service.service.MessageService;
import com.service.common_service.service.ResponseService;
import com.service.common_service.vo.response.CmnResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.service.common_service.constants.MessageConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResponseServiceImpl implements ResponseService {

    private final MessageService messageService;

    @Override
    public CmnResponseVo getLoginFail(String langCode) {
        CmnResponseVo cmnResponseVo = new CmnResponseVo();
        cmnResponseVo.setStatusCode(HttpStatus.UNAUTHORIZED);
        cmnResponseVo.setMessage(messageService.getMessage(langCode,null, FAIL_LOGIN));
        cmnResponseVo.setResultData(null);
        return cmnResponseVo;
    }

    @Override
    public CmnResponseVo getCreateUserSuccess() {
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null,CREATE_ACCOUNT_SUCCESS))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
//                .resultData(userInfo)
                .build();
    }

    @Override
    public CmnResponseVo getCreateUserFail() {
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null,CREATE_ACCOUNT_FAIL))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .resultData(null)
                .build();
    }

    @Override
    public CmnResponseVo getNotAccessUserId(String langCode) {
        CmnResponseVo cmnResponseVo = new CmnResponseVo();
        cmnResponseVo.setStatusCode(HttpStatus.FORBIDDEN);
        cmnResponseVo.setMessage(messageService.getMessage(langCode,null,FAIL_ACCESS_ACCOUNT));
        return cmnResponseVo;
    }

    @Override
    public CmnResponseVo getLoginSuccess(String langCode) {
        CmnResponseVo cmnResponseVo = new CmnResponseVo();
        cmnResponseVo.setStatusCode(HttpStatus.OK);
        cmnResponseVo.setMessage(messageService.getMessage(langCode,null,LOGIN_SUCCESS));
        return cmnResponseVo;
    }

    @Override
    public CmnResponseVo getNotExistUserId() {
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null,NOT_EXIST_ACCOUNT))
                .statusCode(HttpStatus.NO_CONTENT)
                .build();
    }


    @Override
    public CmnResponseVo getSearchSuccess(){
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null, SEARCH_SUCCESS))
                .statusCode(HttpStatus.OK)
                .build();
    }

    @Override
    public CmnResponseVo getSearchFail(){
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null, SEARCH_FAIL))
                .statusCode(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public CmnResponseVo getModifySuccess() {
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null, MODIFY_SUCCESS))
                .statusCode(HttpStatus.OK)
                .build();
    }

    @Override
    public CmnResponseVo getModifyFailed() {
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null, MODIFY_FAIL))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @Override
    public CmnResponseVo getModifyPartiallySucceed() {
        return CmnResponseVo.builder()
                .message(messageService.getMessage(KO,null, MODIFY_PARTIALLY_SUCCESS))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

}
