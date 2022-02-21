package com.semihbkgr.example.springboot.citation.test.annotation;

import com.semihbkgr.example.springboot.citation.config.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

public class WithMockSecurityUserSecurityContextFactory implements WithSecurityContextFactory<WithMockSecurityUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockSecurityUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        var securityUser = new SecurityConfig.SecurityUser(
                annotation.id(),
                annotation.username(),
                annotation.email(),
                annotation.password(),
                Arrays.asList(annotation.authorities()));
        var authentication = new UsernamePasswordAuthenticationToken(
                securityUser,
                securityUser.getPassword(),
                securityUser.getAuthorities());
        context.setAuthentication(authentication);
        return context;
    }

}
