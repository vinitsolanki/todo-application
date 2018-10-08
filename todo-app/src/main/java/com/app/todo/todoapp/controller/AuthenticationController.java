package com.app.todo.todoapp.controller;

import com.app.todo.todoapp.common.bean.AuthUserRequest;
import com.app.todo.todoapp.common.bean.AuthUserResponseBean;
import com.app.todo.todoapp.common.exception.ApplicationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.app.todo.todoapp.security.SecurityConfiguration.*;


@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public AuthUserResponseBean authenticationRequest(@Valid @RequestBody AuthUserRequest authUserRequest) throws AuthenticationException, ApplicationException {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUserRequest.getUsername(), authUserRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<String> authorities = authentication.getAuthorities().stream().map(grantedAuthority -> ((GrantedAuthority) grantedAuthority).getAuthority()).collect(Collectors.toList());

        String userId = ((User) authentication.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
                .claim("authorities", authorities)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();

        AuthUserResponseBean authUserResponseBean = new AuthUserResponseBean();
        authUserResponseBean.setToken(TOKEN_PREFIX + token);
        authUserResponseBean.setUserId(userId);
        authUserResponseBean.setUserName(authUserRequest.getUsername());
        authUserResponseBean.setAuthorities(authorities);

        return authUserResponseBean;
    }
}