package com.service.user.api.service.impl;

import com.service.core.constants.MessageConstants;
import com.service.core.exception.CustomRuntimeException;
import com.service.core.service.MessageService;
import com.service.core.service.ResponseService;
import com.service.user.api.service.UserAuthService;
import com.service.user.api.vo.data.UserInfoVo;
import com.service.user.api.vo.request.RequestUserInfoVo;
import com.service.user.api.vo.request.RequestUserVo;
import com.service.user.api.vo.response.ResponseUserInfoVo;
import com.service.user.entity.UserAuth;
import com.service.user.entity.UserInfo;
import com.service.user.entity.id.UserAuthId;
import com.service.user.repository.UserAuthRepository;
import com.service.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
public class UserAuthServiceImplV1 implements UserAuthService {

    private final UserInfoRepository userInfoRepository;
    private final UserAuthRepository userAuthRepository;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final OAuth2TokenGenerator<OAuth2Token> tokenGenerator;

    @Override
    public ResponseEntity<ResponseUserInfoVo> api_signIn(RequestUserInfoVo requestUserInfoVo) {

        ResponseUserInfoVo responseUserInfoVo = new ResponseUserInfoVo();
        List<UserInfo> userInfos = new LinkedList<>();
        List<UserAuth> userAuths = new LinkedList<>();
        for(UserInfoVo userInfoVo : requestUserInfoVo.getExecuteList()){
            userInfos.add(userInfoVo.toEntity());
            userAuths.add(UserAuth.builder()
                    .userId(userInfoVo.getUserId())
                    .userPassword(userInfoVo.getUserPassword())
                    .comCd(userInfoVo.getComCd())
                    .roles(List.of("USER"))
                    .build()
            );
        }

        userInfoRepository.saveAll(userInfos);
        userAuthRepository.saveAll(userAuths);
        responseUserInfoVo.setCmnResponse(responseService.getCreateUserSuccess());
        return ResponseEntity.ok(responseUserInfoVo);
    }

    @Override
    public ResponseEntity<ResponseUserInfoVo> api_login(RequestUserVo requestUserVo) {
        ResponseUserInfoVo responseUserInfoVo = new ResponseUserInfoVo();
        String comCd = requestUserVo.getComCd();
        String userId = requestUserVo.getUserId();
        String password = requestUserVo.getUserPassword();

        UserAuth userAuth = userAuthRepository.findById(UserAuthId.builder().comCd(comCd).userId(userId).build()).orElseThrow();

        if(!isAvailableUser(userAuth, userId, password)){
            responseUserInfoVo.setCmnResponse(responseService.getLoginFail(MessageConstants.KO));
            throw new CustomRuntimeException(messageService.getMessage(null, MessageConstants.FAIL_LOGIN));
        }

        // 토큰 생성을 위한 Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, userAuth.getAuthorities());

        // Access Token 생성
        OAuth2TokenContext accessTokenContext = createTokenContext(authentication, OAuth2TokenType.ACCESS_TOKEN);
        OAuth2AccessToken accessToken = (OAuth2AccessToken) tokenGenerator.generate(accessTokenContext);

        // Refresh Token 생성
        OAuth2TokenContext refreshTokenContext = createTokenContext(authentication, OAuth2TokenType.REFRESH_TOKEN);
        OAuth2RefreshToken refreshToken = (OAuth2RefreshToken) tokenGenerator.generate(refreshTokenContext);

        if (accessToken == null || refreshToken == null) {
            throw new CustomRuntimeException("토큰 생성 실패");
        }

        HttpHeaders headers = getCookieHeader(accessToken, refreshToken);
        responseUserInfoVo.setCmnResponse(responseService.getCreateUserSuccess());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseUserInfoVo);
    }

    private HttpHeaders getCookieHeader(OAuth2AccessToken accessToken, OAuth2RefreshToken refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie responseCookie = ResponseCookie.from("access_token", accessToken.getTokenValue())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.of(1, ChronoUnit.HOURS))
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken.getTokenValue())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/auth")       // 리프레시 토큰은 인증 관련 경로에서만 사용
                .maxAge(Duration.ofDays(30)) // 리프레시 토큰 유효기간
                .build();

        headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return headers;
    }

    private boolean isAvailableUser(UserAuth userAuth, String userId, String password) {
        return passwordEncoder.matches(password, userAuth.getUserPassword());
    }

    private OAuth2TokenContext createTokenContext(Authentication authentication, OAuth2TokenType tokenType) {
        Set<String> authorizedScopes = Set.of("read", "write");

        return DefaultOAuth2TokenContext.builder()
                .principal(authentication)
                .tokenType(tokenType)
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .build();
    }

}
