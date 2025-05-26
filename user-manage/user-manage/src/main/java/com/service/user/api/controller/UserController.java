package com.service.user.api.controller;

import com.service.core.constants.ApiConstants;
import com.service.user.api.service.UserService;
import com.service.user.api.vo.request.RequestUserInfoVo;
import com.service.user.api.vo.request.RequestUserVo;
import com.service.user.api.vo.response.ResponseUserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.API_BASE+ApiConstants.API_USER_MANAGE+"/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserInfoVo> updateUserInfo(@RequestBody RequestUserInfoVo requestUserInfoVo) {
        return userService.api_updateUserInfo(requestUserInfoVo);
    }

    @PutMapping
    public ResponseEntity<ResponseUserInfoVo> updateUserAuth(@RequestBody RequestUserInfoVo requestUserInfoVo) {
        return userService.api_changeStatus(requestUserInfoVo);
    }



}
