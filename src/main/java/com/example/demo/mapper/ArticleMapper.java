package com.example.demo.mapper;

import com.example.demo.entity.Article;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper {




    List<Article> selectAll();


    @Select("select * from article where id = #{id}")
    Article selectById(Integer id);

    List<Article> selectAll(Article article);

    void insert(Article article);

    void updateById(Article article);

    void deleteById(Integer id);

    @Select("select count(*) from article where time like '%${date}%'")
    Integer selectCountByDate(String date);
}
