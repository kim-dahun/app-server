package com.service.user.api.vo.response;

import com.service.core.vo.response.CmnResponseVo;
import com.service.user.api.vo.data.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ResponseUserInfoVo extends CmnResponseVo {
    private String userId;
    private String comCd;
    private String userName;
    private String comNm;
}
