package com.service.user.api.controller;

import com.service.core.constants.ApiConstants;
import com.service.user.api.service.UserAuthService;
import com.service.user.api.service.UserService;
import com.service.user.api.vo.request.RequestUserInfoVo;
import com.service.user.api.vo.request.RequestUserVo;
import com.service.user.api.vo.response.ResponseUserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.API_BASE+ApiConstants.API_AUTH_MANAGE)
@RequiredArgsConstructor
@CrossOrigin
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseUserInfoVo> apiSignIn(RequestUserInfoVo requestUserInfoVo) {
        return userAuthService.api_signIn(requestUserInfoVo);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUserInfoVo> login(@RequestBody RequestUserVo requestUserVo) {
        return userAuthService.api_login(requestUserVo);
    }

}
