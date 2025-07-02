package com.example.mdcutilsdemo.mapper;

import com.example.mdcutilsdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    // 根据ID查询用户
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);

    // 查询所有用户
    @Select("SELECT * FROM user")
    List<User> findAll();

    // 根据名称模糊查询
    List<User> findByName(String name);

    // 插入用户
    int insert(User user);

    // 更新用户
    int update(User user);

    // 删除用户
    int delete(Long id);
}
