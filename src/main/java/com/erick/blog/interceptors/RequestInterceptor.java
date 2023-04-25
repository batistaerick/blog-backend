package com.erick.blog.interceptors;

import com.erick.blog.exceptions.HandlerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
@Log4j2
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        try {
            if (request.getHeader("Authorization").isEmpty()) {
                response.sendRedirect("/login");
                return false;
            }
            if (request.getRequestURI().endsWith("/users") && !request.isUserInRole("ROLE_ADMIN")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            if (handler instanceof HandlerMethod method) {
                String methodName = method.getMethod().getName();
                String controllerName = method.getBeanType().getSimpleName();
                log.info("Handling request with method {} in controller {}", methodName, controllerName);
            }
            return true;
        } catch (IOException exception) {
            throw new HandlerException(exception);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
