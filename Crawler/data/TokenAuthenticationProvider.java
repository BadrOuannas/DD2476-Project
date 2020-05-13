3
https://raw.githubusercontent.com/harry-xqb/rent-house/master/src/main/java/com/harry/renthouse/security/TokenAuthenticationProvider.java
package com.harry.renthouse.security;

import com.harry.renthouse.base.ApiResponseEnum;
import com.harry.renthouse.entity.User;
import com.harry.renthouse.exception.BusinessException;
import com.harry.renthouse.util.RedisUtil;
import com.harry.renthouse.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * token认证提供者
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RentHouseUserDetailService rentHouseUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.isAuthenticated()) {
            return authentication;
        }
        // 从 TokenAuthentication 中获取 token
        String token = authentication.getCredentials().toString();
        if (StringUtils.isBlank(token)) {
            return authentication;
        }
        if (!tokenUtil.hasToken(token)) {
            throw new BusinessException(ApiResponseEnum.NOT_VALID_CREDENTIAL);
        }
        tokenUtil.refresh(token);
        String username = tokenUtil.getUsername(token);
        User user = (User)rentHouseUserDetailService.loadUserByUsername(username);
        // 返回新的认证信息，带上 token 和反查出的用户信息
        Authentication auth = new PreAuthenticatedAuthenticationToken(user, token, user.getAuthorities());
        auth.setAuthenticated(true);
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (TokenAuthentication.class.isAssignableFrom(aClass));
    }
}