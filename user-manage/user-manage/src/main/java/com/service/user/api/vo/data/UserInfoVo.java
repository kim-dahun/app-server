package com.service.user.api.vo.data;

import com.service.user.entity.UserAuth;
import com.service.user.entity.UserInfo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInfoVo {


    private String userId;
    private String comCd;
    private UserAuth userAuth;
    private String userName;
    private String phone;
    private String email;
    private String userType;
    private String birthDay;

    public UserInfo toEntity(){
        return UserInfo.builder()
                .userId(userId)
                .comCd(comCd)
                .userAuth(userAuth)
                .userName(userName)
                .phone(phone)
                .email(email)
                .userType(userType)
                .birthDay(birthDay)
                .build();
    }

}
