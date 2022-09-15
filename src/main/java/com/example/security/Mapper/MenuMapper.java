package com.example.security.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.security.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userId);
}
