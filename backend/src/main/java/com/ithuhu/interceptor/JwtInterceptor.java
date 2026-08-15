package com.ithuhu.interceptor;

import com.ithuhu.utils.CurrentHolder;
import com.ithuhu.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Key;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    // 密钥，必须和登录时生成JWT用的密钥完全一致
    private static final Key SECRET = Keys.hmacShaKeyFor("Z29vZGl1YW9wZW5zb2Z0d2FyZXNlY3JldGtleTEyMzQ1Njc4OQ==".getBytes());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 从请求头里拿 Authorization 字段
        String authHeader = request.getHeader("Authorization");

        // 2. 如果没带，或者格式不对，直接拒绝
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\"}");
            return false;   // false = 不放行
        }



        // 3. 去掉 "Bearer " 前缀，拿到真正的 token 字符串
        String token = authHeader.substring(7);

        // 4. 验证 token，从中取出 userId
        try {
            Claims claims = JwtUtils.parseToken(token); // 如果token无效或过期，这里会抛异常

            Long userId = claims.get("id", Long.class);
            //将用户id放入Threadlocal中
            CurrentHolder.setCurrentUserId(userId);

            return true;   // 验证通过，放行

        } catch (Exception e) {
            e.printStackTrace();
            // token 无效 / 过期 / 被篡改
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"令牌无效或已过期\"}");
            return false;
        }
    }
}
