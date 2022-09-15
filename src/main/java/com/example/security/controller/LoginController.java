package com.example.security.controller;

import com.example.security.domain.ResponseResult;
import com.example.security.domain.User;
import com.example.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: hu chang
 * Date: 2022/9/12
 * Time: 15:23
 * Description:
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * c.自定义登陆接口
     * 分析需求：
     *
     * 1 自定义一个controller登陆接口
     *
     * 2 放行自定义登陆接口
     *
     * 3使用ProviderManager auth方法进行验证
     *
     * 4自己生成jwt给前端
     *
     * 5系统用户相关所有信息放入redis
     */

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        return loginService.login(user);
    }

    @GetMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
