package com.service.account_manage.api.accoun_code.vo.response;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
import com.service.core.vo.response.CmnResponseVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ResponseAccountCodeDetailVo extends CmnResponseVo {

    private List<AccountCodeDetailVo> dataList;

}
