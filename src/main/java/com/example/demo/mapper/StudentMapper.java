package com.example.demo.mapper;

import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {




    List<Student> selectAll();


    @Select("select * from student where id = #{id}")
    Student selectById(Integer id);

    List<Student> selectAll(Student student);

    void insert(Student student);

    void updateById(Student student);

    void deleteById(Integer id);

    @Select("select * from student where username = #{username}")
    Student selectByUsername(String username);

    @Select("select * from student where no = #{no}")
    Student selectByNo(String no);

}
