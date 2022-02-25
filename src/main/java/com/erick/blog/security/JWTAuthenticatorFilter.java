package com.erick.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.erick.blog.data.UserDetailData;
import com.erick.blog.entities.User;
import com.erick.blog.exceptions.AuthenticateUserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class JWTAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

    public static final Integer EXPIRATION_TOKEN = 600_000;
    public static final String PASSWORD_TOKEN = "a03e7ede-7ec1-49ae-8415-da8d9213cbaa";

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new AuthenticateUserException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        try {
            UserDetailData userData = (UserDetailData) authResult.getPrincipal();
            String token = JWT
                    .create()
                    .withSubject(userData.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                    .sign(Algorithm.HMAC512(PASSWORD_TOKEN));

            response.getWriter().write(token);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}