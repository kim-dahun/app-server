package com.service.account_manage.entity.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class AccountManagerId implements Serializable {

    private String transactionId;

    private String comCd;

    private String userId;

    private String transactionTimekey;;


}
