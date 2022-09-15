package com.example.security.handle;

import com.alibaba.fastjson.JSON;
import com.example.security.domain.ResponseResult;
import com.example.security.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: hu chang
 * Date: 2022/9/15
 * Time: 10:45
 * Description:
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult(HttpStatus.FORBIDDEN.value(),"没有权限");
        String string = JSON.toJSONString(responseResult);
        WebUtils.renderString(response,string);
    }
}
