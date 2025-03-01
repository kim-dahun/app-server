package com.service.core.vo.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CmnResponseVo {

    private Object statusCode;
    private String message;
    private Object resultData;
    private String etc;


    public void setCmnResponse(CmnResponseVo cmnResponseVo){
        this.statusCode = cmnResponseVo.getStatusCode();
        this.message = cmnResponseVo.getMessage();
        this.etc = cmnResponseVo.getEtc();
    }

}
