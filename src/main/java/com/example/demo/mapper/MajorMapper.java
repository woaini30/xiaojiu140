package com.example.demo.mapper;

import com.example.demo.entity.Major;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MajorMapper {




    List<Major> selectAll();


    @Select("select * from major where id = #{id}")
    Major selectById(Integer id);

    List<Major> selectAll(Major major);

    void insert(Major major);

    void updateById(Major major);

    void deleteById(Integer id);


}
