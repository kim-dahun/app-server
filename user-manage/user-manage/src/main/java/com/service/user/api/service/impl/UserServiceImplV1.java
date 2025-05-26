package com.service.user.api.service.impl;

import com.service.core.constants.MessageConstants;
import com.service.core.exception.CustomRuntimeException;
import com.service.core.service.MessageService;
import com.service.core.service.ResponseService;
import com.service.user.api.service.UserService;
import com.service.user.api.vo.data.UserInfoVo;
import com.service.user.api.vo.request.RequestUserInfoVo;
import com.service.user.api.vo.request.RequestUserVo;
import com.service.user.api.vo.response.ResponseUserInfoVo;
import com.service.user.entity.UserAuth;
import com.service.user.entity.UserInfo;
import com.service.user.entity.id.UserAuthId;
import com.service.user.repository.UserAuthRepository;
import com.service.user.repository.UserInfoRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImplV1 implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final UserAuthRepository userAuthRepository;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ResponseUserInfoVo> api_updateUserInfo(RequestUserInfoVo requestUserInfoVo) {
        ResponseUserInfoVo responseUserInfoVo = new ResponseUserInfoVo();
        List<UserInfo> userInfos = new LinkedList<>();
        List<UserAuth> userAuths = new LinkedList<>();
        for(UserInfoVo userInfoVo : requestUserInfoVo.getExecuteList()){
            userInfos.add(userInfoVo.toEntity());
            if(userInfoVo.getUserPassword()!=null){
                userAuths.add(UserAuth.builder()
                        .userId(userInfoVo.getUserId())
                        .userPassword(userInfoVo.getUserPassword())
                        .comCd(userInfoVo.getComCd())
                        .roles(List.of("USER"))
                        .build()
                );
            }
        }
        userInfoRepository.saveAll(userInfos);
        userAuthRepository.saveAll(userAuths);
        responseUserInfoVo.setCmnResponse(responseService.getCreateUserSuccess());
        return ResponseEntity.ok(responseUserInfoVo);
    }

    @Override
    public ResponseEntity<ResponseUserInfoVo> api_changeStatus(RequestUserInfoVo requestUserInfoVo) {
        ResponseUserInfoVo responseUserInfoVo = new ResponseUserInfoVo();
        List<UserAuth> userAuths = new LinkedList<>();
        for(UserInfoVo userInfoVo : requestUserInfoVo.getExecuteList()){
            UserAuth userAuth = userAuthRepository.findById(UserAuthId.builder().userId(userInfoVo.getUserId()).comCd(userInfoVo.getComCd()).build()).orElse(null);
            if(userAuth != null && isAvailableUser(userAuth, userAuth.getUserId(), userAuth.getPassword())){
                userAuth.setStatus(requestUserInfoVo.getStatus());
                userAuths.add(userAuth);
            }
        }
        userAuthRepository.saveAll(userAuths);
        responseUserInfoVo.setCmnResponse(responseService.getModifySuccess());
        return ResponseEntity.ok(responseUserInfoVo);
    }

    private boolean isAvailableUser(UserAuth userAuth, String userId, String password) {
        return passwordEncoder.matches(password, userAuth.getUserPassword());
    }
}
