package com.service.user.auth;

import com.service.user.entity.id.UserAuthId;
import com.service.user.repository.UserAuthRepository;
import com.service.user.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return userAuthRepository.findById(
                    UserAuthId.builder()
                            .comCd(request.getAttribute("comCd").toString())
                            .userId(username)
                            .build()
            ).orElseThrow(()    -> new UsernameNotFoundException(username + " not found"));
        } catch (Exception e){
            return null;
        }

    }
}
