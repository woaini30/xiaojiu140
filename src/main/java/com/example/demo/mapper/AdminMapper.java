package com.example.demo.mapper;

import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {




    List<Admin> selectAll();


    @Select("select * from admin where id = #{id}")
    Admin selectById(Integer id);

    List<Admin> selectAll(Admin admin);

    void insert(Admin admin);

    void updateById(Admin admin);

    void deleteById(Integer id);

    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
