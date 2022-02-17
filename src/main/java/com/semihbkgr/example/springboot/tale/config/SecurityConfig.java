package com.semihbkgr.example.springboot.tale.config;

import com.semihbkgr.example.springboot.tale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .formLogin().loginPage("/login").and()
                .authorizeExchange().pathMatchers("/login", "/signup").permitAll().anyExchange().authenticated();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserDetailsService(userService);
    }

    @Bean
    @Profile("dev")
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoderDev() {
        return new DelegatingPasswordEncoder("bcrypt",
                Map.of(
                        "bcrypt", new BCryptPasswordEncoder(),
                        "noop", NoOpPasswordEncoder.getInstance()));
    }

    @Bean
    @Profile("pro")
    public PasswordEncoder passwordEncoderPro() {
        return new BCryptPasswordEncoder();
    }

    @RequiredArgsConstructor
    public static class UserDetailsService implements ReactiveUserDetailsService {

        public static final String DEFAULT_AUTHORITY = "ROLE_USER";

        private final UserService userService;

        @Override
        public Mono<UserDetails> findByUsername(String username) {
            return userService.findByUsername(username)
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))
                    .map(user -> User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .authorities(DEFAULT_AUTHORITY)
                            .build());
        }

    }

}
