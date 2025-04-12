package com.service.user.entity;

import com.service.core.entity.CmnBaseCUDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "COM_INFO", schema = "USER_MANAGE")
public class ComInfo extends CmnBaseCUDEntity {
    @Id
    @Size(max = 20)
    @Column(name = "COM_CD", nullable = false, length = 20)
    private String comCd;

    @Size(max = 100)
    @Column(name = "COM_NM", length = 100)
    private String comNm;

    @Size(max = 100)
    @Column(name = "REPRESENTATIVE", length = 100)
    private String representative;

    @Size(max = 20)
    @Column(name = "TEL", length = 20)
    private String tel;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "PHONE", length = 20)
    private String phone;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "PHONE2", length = 20)
    private String phone2;

    @jakarta.validation.constraints.Size(max = 400)
    @Column(name = "ADDRESS", length = 400)
    private String address;

    @jakarta.validation.constraints.Size(max = 400)
    @Column(name = "ADDRESS_DETAIL", length = 400)
    private String addressDetail;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "ADDRESS_NO", length = 20)
    private String addressNo;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "COM_SIGN_NO", length = 20)
    private String comSignNo;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "STATUS", length = 20)
    private String status;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "MEMBER_GRADE", length = 20)
    private String memberGrade;

}