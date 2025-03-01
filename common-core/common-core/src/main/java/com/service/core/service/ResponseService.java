package com.service.core.service;


import com.service.core.vo.response.CmnResponseVo;

public interface ResponseService {

    CmnResponseVo getNotExistUserId();

    CmnResponseVo getLoginFail(String langCode);

    CmnResponseVo getCreateUserSuccess();

    CmnResponseVo getCreateUserFail();

    CmnResponseVo getNotAccessUserId(String langCode);

    CmnResponseVo getLoginSuccess(String langCode);

    CmnResponseVo getSearchSuccess();

    CmnResponseVo getSearchFail();

    CmnResponseVo getModifySuccess();

    CmnResponseVo getModifyFailed();

    CmnResponseVo getModifyPartiallySucceed();

}
