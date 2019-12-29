package org.jarvis.kk.security;

import org.jarvis.kk.service.OAuth2MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfig
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2MemberService oauth2MemberService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAuthenticationLoginSuccessHandler restAuthenticationLoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
        .and().headers().frameOptions().disable()
        .and().authorizeRequests()
        .antMatchers("/kk/msg", "/admin/**").permitAll()
        .anyRequest().authenticated()
        .and().logout().logoutSuccessUrl("/").logoutUrl("/kk/logout").invalidateHttpSession(true).permitAll()
        .and().oauth2Login().successHandler(restAuthenticationLoginSuccessHandler).userInfoEndpoint().userService(oauth2MemberService);
    }
    
}