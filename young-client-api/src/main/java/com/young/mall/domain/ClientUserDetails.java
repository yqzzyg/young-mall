package com.young.mall.domain;

import com.young.db.entity.YoungUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Description: 用户端
 * @Author: yqz
 * @CreateDate: 2020/11/24 9:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUserDetails implements UserDetails {

    private YoungUser youngUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return youngUser.getPassword();
    }

    @Override
    public String getUsername() {
        return youngUser.getUsername();
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
        return youngUser.getStatus() == 0;
    }
}
