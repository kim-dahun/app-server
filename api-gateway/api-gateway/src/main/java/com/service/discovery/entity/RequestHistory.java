package com.service.discovery.entity;

import com.service.core.constants.ApiConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("REQUEST_HISTORY")

public class RequestHistory {

    @Id
    private String requestId;

    @Id
    private String clientIp;

    private String requestType;

    private String clientDeviceNm;

    private String requestEndPoint;

    private String requestToken;

    private LocalDateTime requestDateTime;

}