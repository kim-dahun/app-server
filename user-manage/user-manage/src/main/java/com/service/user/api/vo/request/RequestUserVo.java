package com.service.user.api.vo.request;

import com.service.core.constants.CommonConstants;
import com.service.core.vo.request.CmnRequestVo;
import com.service.user.entity.UserAuth;
import com.service.user.entity.id.UserInfoId;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class RequestUserVo  extends CmnRequestVo {

    private String userId;
    private String userName;
    private String comCd;
    private String userPassword;
    private String status;
    private List<String> roles;

    public UserAuth toAuthEntity(){
        return new UserAuth(comCd,userId,userPassword, "NORMAL",roles);
    }

    public UserInfoId toUserInfoId(){
        return new UserInfoId(userId,comCd);
    }

}
