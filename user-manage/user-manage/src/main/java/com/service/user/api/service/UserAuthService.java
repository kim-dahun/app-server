package com.service.user.api.service;

import com.service.user.api.vo.request.RequestUserInfoVo;
import com.service.user.api.vo.request.RequestUserVo;
import com.service.user.api.vo.response.ResponseUserInfoVo;
import org.springframework.http.ResponseEntity;

public interface UserAuthService {

    ResponseEntity<ResponseUserInfoVo> api_signIn(RequestUserInfoVo requestUserInfoVo);

    ResponseEntity<ResponseUserInfoVo> api_login(RequestUserVo requestUserVo);

}
