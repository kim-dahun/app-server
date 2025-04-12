package com.service.discovery.entity.id;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestHistoryId implements Serializable {

    @Serial
    private static final long serialVersionUID = 2391100148687379720L;


    private String requestId;

    private String clientIp;

}