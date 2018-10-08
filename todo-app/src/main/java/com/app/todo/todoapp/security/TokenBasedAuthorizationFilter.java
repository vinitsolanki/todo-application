package com.app.todo.todoapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.app.todo.todoapp.security.SecurityConfiguration.*;

public class TokenBasedAuthorizationFilter extends BasicAuthenticationFilter {

    TokenBasedAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationToken != null && authorizationToken.startsWith(TOKEN_PREFIX)) {
            authorizationToken = authorizationToken.replaceFirst(TOKEN_PREFIX, "");
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(authorizationToken);
            List<SimpleGrantedAuthority> grantedAuthorities = ((List<String>) claims.getBody().get("authorities")).stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
            String username = claims.getBody().getSubject();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities));
        }

        chain.doFilter(request, response);
    }
}
