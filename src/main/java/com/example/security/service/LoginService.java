package com.example.security.service;

import com.example.security.domain.ResponseResult;
import com.example.security.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
