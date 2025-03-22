package com.service.core.vo.response;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class CmnResponseVo {

    private Object statusCode;
    private String message;
    private Object commonResultData;
    private List commonResultList;
    private Map commonResultMap;
    private String etc;


    public void setCmnResponse(CmnResponseVo cmnResponseVo){
        this.statusCode = cmnResponseVo.getStatusCode();
        this.message = cmnResponseVo.getMessage();
        this.etc = cmnResponseVo.getEtc();
    }

}
