package com.service.user.api;

import com.service.user.api.vo.request.RequestUserInfoVo;
import com.service.user.api.vo.request.RequestUserVo;
import com.service.user.api.vo.response.ResponseUserInfoVo;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<ResponseUserInfoVo> api_signIn(RequestUserInfoVo requestUserInfoVo);

    ResponseEntity<ResponseUserInfoVo> api_login(RequestUserVo requestUserVo);

    ResponseEntity<ResponseUserInfoVo> api_updateUserInfo(RequestUserInfoVo requestUserInfoVo);

    ResponseEntity<ResponseUserInfoVo> api_changeStatus(RequestUserInfoVo requestUserInfoVo);

}
