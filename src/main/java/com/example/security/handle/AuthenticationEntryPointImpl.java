package com.example.security.handle;

import com.alibaba.fastjson.JSON;
import com.example.security.domain.ResponseResult;
import com.example.security.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: hu chang
 * Date: 2022/9/15
 * Time: 10:41
 * Description:
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult(HttpStatus.UNAUTHORIZED.value(),"登陆认证失败");
        String string = JSON.toJSONString(responseResult);
        WebUtils.renderString(response,string);
    }
}
