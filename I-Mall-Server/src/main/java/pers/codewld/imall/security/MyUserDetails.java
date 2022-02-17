package pers.codewld.imall.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * <p>
 * 自定义的UserDetails类
 * </p>
 * <p>
 * 描述：对UserDetails的封装，增加ID、Setter
 * </p>
 *
 * @author codewld
 * @since 2022-02-11
 */
@Data
public class MyUserDetails implements UserDetails {

    private Long id;

    private String username;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return true;
    }
}
