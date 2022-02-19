package com.semihbkgr.example.springboot.citation.config;

import com.semihbkgr.example.springboot.citation.service.UserService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .httpBasic()
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/signup")
                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .build();
    }

    @Bean
    public SecurityUserService userDetailsService(UserService userService) {
        return new SecurityUserService(userService);
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

    @Getter
    @EqualsAndHashCode(callSuper = true)
    public static class SecurityUser extends User implements Serializable {

        private final int id;
        private final String email;

        public SecurityUser(int id, String username, String email, String password, Collection<String> authorities) {
            super(username, password, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
            this.id = id;
            this.email = email;
        }

    }

    @RequiredArgsConstructor
    public static class SecurityUserService implements ReactiveUserDetailsService {

        private final UserService userService;

        @Override
        public Mono<UserDetails> findByUsername(String username) {
            return userService.findByUsername(username)
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))
                    .map(user ->
                            new SecurityUser(
                                    user.getId(),
                                    user.getUsername(),
                                    user.getEmail(),
                                    user.getPassword(),
                                    Arrays.stream(user.getAuthorities().split(",")).collect(Collectors.toSet())));
        }

    }

}
