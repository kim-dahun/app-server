package com.service.discovery.entity;

import com.service.core.constants.ApiConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
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
    @Column("REQUEST_ID")
    private String requestId;

    @Column("CLIENT_IP")
    private String clientIp;

    @Column("REQUEST_TYPE")
    private String requestType;

    @Column("CLIENT_DEVICE_NM")
    private String clientDeviceNm;

    @Column("REQUEST_END_POINT")
    private String requestEndPoint;

    @Column("REQUEST_TOKEN")
    private String requestToken;

    @Column("REQUEST_DATE_TIME")
    private LocalDateTime requestDateTime;

    @Version
    private Long version;

}