package com.service.user.api.vo.request;

import com.service.core.vo.request.CmnRequestVo;
import com.service.user.api.vo.data.UserInfoVo;
import com.service.user.entity.UserAuth;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class RequestUserInfoVo extends CmnRequestVo {

    List<UserInfoVo> executeList;


}
