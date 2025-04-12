package com.service.user.entity.id;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserAuthId implements Serializable {
    public String comCd;

    public String userId;


}