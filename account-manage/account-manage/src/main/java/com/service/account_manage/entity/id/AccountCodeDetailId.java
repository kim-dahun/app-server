package com.service.account_manage.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCodeDetailId implements Serializable {

    private String userId;

    private String codeId;

    private String codeGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCodeDetailId that = (AccountCodeDetailId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(codeId, that.codeId) &&
                Objects.equals(codeGroup, that.codeGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, codeId, codeGroup);
    }
}