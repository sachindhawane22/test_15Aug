package com.hackerrank.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("john_doe")
                .password("{noop}student_password")
                .authorities("ROLE_STUDENT_USER")
                .and()
                .withUser("jane_doe")
                .password("{noop}admin_password")
                .authorities("ROLE_OFFICE_ADMIN");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/*").permitAll().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/course").hasAuthority("ROLE_OFFICE_ADMIN")
                .antMatchers(HttpMethod.POST,"/student").hasAnyAuthority("ROLE_OFFICE_ADMIN","ROLE_STUDENT_USER")
                .anyRequest()
                .authenticated()
        .and()
        .httpBasic();
    }
}
