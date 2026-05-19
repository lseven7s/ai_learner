package com.ai.learning.interceptor;

import com.ai.learning.common.ResultCode;
import com.ai.learning.exception.BusinessException;
import com.ai.learning.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(JwtUtil.TOKEN_HEADER);
        
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "未登录，请先登录");
        }
        
        if (token.startsWith(JwtUtil.TOKEN_PREFIX)) {
            token = token.substring(JwtUtil.TOKEN_PREFIX.length());
        }
        
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "token无效或已过期，请重新登录");
        }
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);
        
        return true;
    }
}
