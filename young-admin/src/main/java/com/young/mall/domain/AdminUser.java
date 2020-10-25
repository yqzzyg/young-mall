package com.young.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * @Description: 实现 UserDetails 接口，完善用户信息
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser implements UserDetails {
    private String username;
    private String password;

    public static final Boolean NOT_DELETED = false;
    public static final Boolean IS_DELETED = true;
    private Integer id;

    private String lastLoginIp;
    private Date lastLoginTime;
    private String avatar;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;
    private Integer[] roleIds;

    private Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }
}

