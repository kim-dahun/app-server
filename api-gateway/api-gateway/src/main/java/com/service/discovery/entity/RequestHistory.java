package com.service.discovery.entity;

import com.service.discovery.entity.id.RequestHistoryId;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(RequestHistoryId.class)
@Table(name = "REQUEST_HISTORY")
public class RequestHistory {

    @Id
    @Column(name = "REQUEST_ID", nullable = false, length = 40)
    private String requestId;

    @Id
    @Column(name = "CLIENT_IP", nullable = false, length = 100)
    private String clientIp;

    @Column(name = "REQUEST_TYPE", length = 50)
    private String requestType;

    @Column(name = "CLIENT_DEVICE_NM", length = 100)
    private String clientDeviceNm;

    @Column(name = "REQUEST_END_POINT", length = 1000)
    private String requestEndPoint;

    @Column(name = "REQUEST_TOKEN", length = 4000)
    private String requestToken;

    @Column(name = "REQUEST_DATE_TIME")
    private Instant requestDateTime;

}