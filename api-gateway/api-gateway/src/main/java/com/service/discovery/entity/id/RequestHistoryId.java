package com.service.discovery.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class RequestHistoryId implements Serializable {

    private static final long serialVersionUID = 2391100148687379720L;


    private String requestId;

    private String clientIp;

}