package com.example.security.service.Impl;

import com.example.security.config.RedisConfig;
import com.example.security.domain.LoginUser;
import com.example.security.domain.ResponseResult;
import com.example.security.domain.User;
import com.example.security.service.LoginService;
import com.example.security.util.JwtUtil;
import com.example.security.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: hu chang
 * Date: 2022/9/12
 * Time: 15:26
 * Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

//  1 自定义一个controller登陆接口

//2 放行自定义登陆接口

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {


        //3使用ProviderManager auth方法进行验证
        /**
         * 从UsernamePasswordAuthenticationToken（为authentication的子类）中设置请求过来的用户名和密码
         * authenticationManager.authenticate(usernamePasswordAuthenticationToken);自带的方法与数据库的用户匹配
         * 调用了UserDetailServiceImpl中重写了UserDetailsService中的方法loadUserByUsername，自己重写的方法与数据库匹配
         * 前端来的数据或者从上游下游获取来的数据都要来判断数据是否为空
         * 调用工具类的jwtUtil中的方法，获取token，存放到map中（根据前端的需要）
         * 调用rediscach的工具类方法，存放到redis缓存中，调用本机redis不需要配置
         */
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (Objects.isNull(authenticate)){

            throw new RuntimeException("密码或者用户名错误");
        }

        //4自己生成jwt给前端
        LoginUser loginUser = (LoginUser)(authenticate.getPrincipal());
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);

        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        //5系统用户相关所有信息放入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        return new ResponseResult(200,map);
    }

    /**
     *
     * 登出接口
     * 从securitycontextholder中获取authentication 去得到userid 得到redis中的key
     * 删除
     */

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser =(LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        redisCache.deleteObject("login:"+userId);
        return null;
    }
}
