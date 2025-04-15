package com.service.user.entity;

import com.service.core.entity.CmnBaseCUDEntity;
import com.service.user.entity.id.UserAuthId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(UserAuthId.class)
@Entity
@Table(name = "USER_AUTH", schema = "USER_MANAGE")
public class UserAuth extends CmnBaseCUDEntity implements UserDetails {

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}