package com.smartjob.auth.controllers;

import com.myzlab.k.DynamicObject;
import com.smartjob.auth.common.helpers.encryptor.TokenHelper;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenHelper tokenHelper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                DynamicObject.create()
                    .add("timestamp", LocalDateTime.now())
                    .add("error", "Unauthorized")
                    .add("status", 401)
                    .add("message", "Authorization token is missing")
                .toJSON()
            );
            
            return false;
        }
        
        tokenHelper.decodeAccessToken(authorizationHeader.substring(7));

        return true;
    }
}
