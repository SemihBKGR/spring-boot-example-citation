package com.semihbkgr.example.springboot.citation.test.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockSecurityUserSecurityContextFactory.class)
public @interface WithMockSecurityUser {

    int id() default 0;

    String username() default "username";

    String email() default "email";

    String password() default "password";

    String[] authorities() default {"ROLE_USER"};

}
