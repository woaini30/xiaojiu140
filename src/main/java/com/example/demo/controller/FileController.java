package com.example.demo.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.demo.common.Result;
import com.example.demo.entity.Student;
import com.example.demo.exception.CustomException;
import com.example.demo.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")

public class FileController {

    private static final  String filePath = System.getProperty("user.dir") + "/files/";
    private final StudentService studentService;

    public FileController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }

        String fileName = System.currentTimeMillis() + "_" + originalFilename ;
        String realPath = filePath + fileName;
        try {
           FileUtil.writeBytes(file.getBytes(), realPath);
        } catch (IOException e) {
            throw new CustomException("500","文件上传失败");
        }
        //返回一个网络连接
        String url = "http://localhost:8081/files/download/" + fileName;
        return Result.success(url);
    }
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
           OutputStream os = response.getOutputStream();
           String realPath = filePath + fileName;
           byte[] bytes =  FileUtil.readBytes(realPath);
           os.write(bytes);
           os.flush();
           os.close();
        } catch (IOException e) {
            throw new CustomException("500","上传失败");
        }
    }

    @PostMapping("/wang/upload")
    public Map<String, Object> wangEdit(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }

        String fileName = System.currentTimeMillis() + "_" + originalFilename ;
        String realPath = filePath + fileName;
        try {
            FileUtil.writeBytes(file.getBytes(), realPath);
        } catch (IOException e) {
            throw new CustomException("500","文件上传失败");
        }
        //返回一个网络连接
        String url = "http://localhost:8081/files/download/" + fileName;
        Map<String,Object> resMap = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> urlMap = new HashMap<>();
        urlMap.put("url",url);
        list.add(urlMap);
        resMap.put("errno",0);
        resMap.put("data",list);
        return resMap;
    }

}
