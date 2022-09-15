package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hu chang
 * Date: 2022/9/10
 * Time: 10:23
 * Description:
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('dev:code:pull')")
    public String hello(){
        return "welcome:ww";
    }
}
