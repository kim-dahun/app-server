package com.service.user.entity;

import com.service.core.entity.CmnBaseCUDEntity;
import com.service.user.entity.id.UserAuthId;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(UserAuthId.class)
@Entity
@Table(name = "USER_AUTH", schema = "USER_MANAGE")
public class UserAuth extends CmnBaseCUDEntity {

    @Id
    @jakarta.validation.constraints.Size(max = 50)
    @jakarta.validation.constraints.NotNull
    @Column(name = "COM_CD", nullable = false, length = 50)
    private String comCd;

    @Id
    @jakarta.validation.constraints.Size(max = 50)
    @jakarta.validation.constraints.NotNull
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @jakarta.validation.constraints.Size(max = 50)
    @Column(name = "USER_PASSWORD", length = 50)
    private String userPassword;

    @jakarta.validation.constraints.Size(max = 100)
    @Column(name = "STATUS", length = 100)
    private String status;

}