package com.service.account_manage.api.accoun_code.vo.response;

import com.service.account_manage.api.accoun_code.vo.data.AccountCodeDetailVo;
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
public class ResponseAccountCodeDetailVo {

    private List<AccountCodeDetailVo> dataList;

}
