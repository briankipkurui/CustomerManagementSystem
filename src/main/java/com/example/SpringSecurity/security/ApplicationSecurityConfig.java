package com.example.SpringSecurity.security;


import com.example.SpringSecurity.auth.ApplicationUserService;
import com.example.SpringSecurity.jwt.JwtTokenVerifier;
import com.example.SpringSecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.SpringSecurity.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {
    private final ApplicationUserService applicationUserService;
    private  final PasswordConfig passwordConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

                JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter =
                new JwtUsernameAndPasswordAuthenticationFilter(authenticationManagerBean());
                jwtUsernameAndPasswordAuthenticationFilter.setFilterProcessesUrl("/api/v1/registration/login");
                http.csrf().disable();

                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                        .addFilter(jwtUsernameAndPasswordAuthenticationFilter)
                .addFilterAfter(new JwtTokenVerifier(),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers("/api/v*/registration/**")
                .permitAll()
                .antMatchers("/management/api/v1/customer/**")
                .permitAll()
                .antMatchers("/api/**").hasRole(CUSTOMER.name())
                .anyRequest()
                .authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder((passwordConfig.bCryptPasswordEncoder()));
        provider.setUserDetailsService(applicationUserService);
        return provider;

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}