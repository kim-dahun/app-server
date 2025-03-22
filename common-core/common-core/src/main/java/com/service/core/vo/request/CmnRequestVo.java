package com.service.core.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CmnRequestVo {

    private String crudFlag;

    private String requestId;
    private String requestComCd;

    private Integer pageNum;
    private Integer pageSize;

}
