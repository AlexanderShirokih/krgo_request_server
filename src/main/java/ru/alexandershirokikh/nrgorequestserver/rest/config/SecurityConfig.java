package ru.alexandershirokikh.nrgorequestserver.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Responsible for configuring access to server REST endpoints
 */
@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/*").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.POST, "/*").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.POST, "/districts").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/districts").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/streets").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/streets").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/counters").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/counters").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/requests/types").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/requests/types").hasRole(ADMIN)
                .anyRequest()
                .authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
