package com.example.demo.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.demo.common.Result;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;
    /**
     *查询所有数据
     */
    @GetMapping("/selectAll")
    public Result selectAll(Student student) {
        List<Student> list = studentService.selectAll(student);
        return Result.success(list);
    }
    /**
     *通过id查询数据
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Student student = studentService.selectById(id);
        return Result.success(student);
    }
    /**
     *分页处理
     */
    @GetMapping("/selectPage")
    public Result selectPage(Student student,
            @RequestParam(defaultValue = "1") Integer pageNum ,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<Student> pageInfo = studentService.selectPage(student,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    /**
     *添加单个数据
     */
    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
       studentService.add(student);
        return Result.success();
    }
    /**
     *添更新数据
     */
    @PutMapping("/update")
    public Result update(@RequestBody Student student) {
        studentService.update(student);
        return Result.success();
    }
    /**
     *删除单个数据
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        studentService.deleteById(id);
        return Result.success();
    }
    /**
     *批量删除数据
     * */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        studentService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * *导出数据
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        List<Student> studentList = studentService.selectAll(null);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("name","姓名");
        writer.addHeaderAlias("username","账号");
        writer.addHeaderAlias("no","学号");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("majorin","专业");
        writer.setOnlyAlias(true);
        writer.write(studentList,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream os= response.getOutputStream();
        writer.flush(os);
        writer.close();
    }
    /**
     * 文件导入
     */
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("姓名","name");
        reader.addHeaderAlias("账号","username");
        reader.addHeaderAlias("学号","no");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("专业","majorin");
        List<Student> studentList = reader.readAll(Student.class);
        for (Student student : studentList) {
            studentService.add(student);
        }
        return Result.success();
    }

    // Spring Boot配置类
    @Configuration
    public static class CorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://lwc520.com") // 前端域名
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }
}
