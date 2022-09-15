package com.example.security.filter;

import com.example.security.domain.LoginUser;
import com.example.security.util.JwtUtil;
import com.example.security.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: hu chang
 * Date: 2022/9/12
 * Time: 16:36
 * Description: OncePerRequestFilter 只走一次
 *  容器之间的循环引用会报错
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;


    /**
     *
     * 整个security有16个过滤器，自己写个认证过滤器，把这个过滤器排在第一个
     * 作用：
     * 1.判断是否有token（是否登陆）
     * 2.解析token得到loginuser封装到Authentication中，存入SecurityContextHolder中使其他功能需要此证明来得到用户的所有信息
     *
     *
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.获取token 看前端封装到header还是cookie中
        String token = request.getHeader("token");

        if (Objects.isNull(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //2.解析token
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //3.获取userid
        LoginUser loginUser =(LoginUser) redisCache.getCacheObject("login:" + userId);

        //4.封装Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null, loginUser.getAuthorities());

        //5.存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request,response);
    }

}
