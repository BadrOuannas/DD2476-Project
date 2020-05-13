3
https://raw.githubusercontent.com/harry-xqb/rent-house/master/src/main/java/com/harry/renthouse/security/TokenAuthentication.java
package com.harry.renthouse.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * token 凭证
 * @author Harry Xu
 * @date 2020/5/11 9:51
 */
@AllArgsConstructor
public class TokenAuthentication implements Authentication {

    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
