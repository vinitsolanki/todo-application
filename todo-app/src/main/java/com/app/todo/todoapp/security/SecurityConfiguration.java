package com.app.todo.todoapp.security;

import com.app.todo.todoapp.common.enums.UserType;
import com.app.todo.todoapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String CSRF_HEADER = "X-CSRF-TOKEN";

    public static final long TOKEN_LIFETIME = 1000 * 60 * 60 * 24 * 7; //7 Days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_SECRET = Base64.getEncoder().encodeToString("1234567890".getBytes());
    private StatelessCsrfFilter statelessCsrfFilter = new StatelessCsrfFilter();

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .addFilterBefore(statelessCsrfFilter, CsrfFilter.class);

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .addFilter(new TokenBasedAuthorizationFilter(authenticationManager()));

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(permittedUrls()).permitAll()
                .antMatchers("/admin/**").hasAuthority(UserType.ADMIN.name())
                .antMatchers("/**").authenticated();

        //h2-console configurations
        http.headers().frameOptions().disable();
    }

    private static final String[] permittedUrls() {
        return new String[] { "/auth/login", "/auth/logout", "/h2-console/**" };
    }
}
