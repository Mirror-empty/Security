package com.example.security;

import com.example.security.Mapper.MenuMapper;
import com.example.security.Mapper.UserMapper;
import com.example.security.config.SecurityConfig;
import com.example.security.domain.User;
import com.example.security.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SecurityApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        /**
         * BCryptPasswordEncoder security自带的加密方式
         */
        List<User> users = userMapper.selectList(null);
        System.out.println(users.get(0));
    }

    @Test
    void testBcript(){
//        每次加盐出来都不一样
        String encode = passwordEncoder.encode("ydlclass");
        String encode1 = passwordEncoder.encode("123456");
        System.out.println(encode);
        System.out.println(encode1);
        boolean matches = passwordEncoder.matches("123456", encode);
        System.out.println(matches);
    }

    @Test
    void testChain(){
        Long id=1L;
        List<String> list = menuMapper.selectPermsByUserId(id);
        for (String st:list
             ) {
            System.out.println(st);
        }
    }

}
