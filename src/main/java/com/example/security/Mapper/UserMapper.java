package com.example.security.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.security.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: hu chang
 * Date: 2022/9/11
 * Time: 16:52
 * Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
