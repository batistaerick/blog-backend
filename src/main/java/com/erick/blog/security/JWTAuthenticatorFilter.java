package com.erick.blog.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.erick.blog.data.UserDetailData;
import com.erick.blog.entities.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

    public static final Integer EXPIRATION_TOKEN = 600_000;

    public static final String PASSWORD_TOKEN = "a03e7ede-7ec1-49ae-8415-da8d9213cbaa";

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticatorFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper()
            .readValue(request.getInputStream(), User.class);

            return authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to authenticate user", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        UserDetailData userData = (UserDetailData) authResult.getPrincipal();
        String token = JWT
        .create()
        .withSubject(userData.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
        .sign(Algorithm.HMAC512(PASSWORD_TOKEN));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}