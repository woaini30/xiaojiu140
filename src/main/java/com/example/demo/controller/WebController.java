package com.example.demo.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.demo.common.Result;
import com.example.demo.entity.Account;
import com.example.demo.entity.Student;
import com.example.demo.exception.CustomException;
import com.example.demo.service.AdminService;
import com.example.demo.service.ArticleService;
import com.example.demo.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class WebController {

    @Resource
    private StudentService studentService;
    @Resource
    private AdminService adminService;
    @Resource
    private ArticleService articleService;

    @GetMapping("/")
    public String home() {
        return "Spring Boot运行成功";
    }

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account result = null;
        if ("ADMIN".equals(account.getRole())) {
          result = adminService.login(account);
        }else if ("STU".equals(account.getRole())) {
            result = studentService.login(account);
        }else {
            throw new CustomException("500","非常规输入");
        }
        return Result.success(result);
    }

    @PostMapping("/register")
    Result register(@RequestBody Student student) {
        studentService.register(student);
        return Result.success();
    }

    @PutMapping("/updatePassword")
    Result updatePassword(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            adminService.updatePassword(account);
        }else if ("STU".equals(account.getRole())) {
            studentService.updatePassword(account);
        }else {
            throw new CustomException("500","非常规输入");
        }
        return Result.success();
    }

    @GetMapping("/barData")
    public Result getBarData() {
        Map<String,Object> map = new HashMap<>();
        List<Student> studentList = studentService.selectAll(null);
        Set<String> majorinSet = studentList.stream().map(Student::getMajorin).collect(Collectors.toSet());
        map.put("major",majorinSet);
        List<Long> countList = new ArrayList<>();
        for (String majorin : majorinSet) {
            long count = studentList.stream().filter(student -> student.getMajorin().equals(majorin)).count();
            countList.add(count);
        }
        map.put("count",countList);
        return Result.success(map);
    }

    @GetMapping("/lineData")
    public Result getLineData() {
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        DateTime start = DateUtil.offsetDay(date,-5);
        List<DateTime> dateTimeList = DateUtil.rangeToList(start,date, DateField.DAY_OF_MONTH);
        List<String> dateStrList = dateTimeList.stream().map(dateTime -> DateUtil.format(dateTime,
                "yyyy年MM月dd日")).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        map.put("date",dateStrList);

        List<Integer>  countList = new ArrayList<>();
        for (DateTime day :dateTimeList){
            String dayFormat = DateUtil.format(day,"yyyy-MM-dd");
            Integer count = articleService.selectCountByDate(dayFormat);
            countList.add(count);
        }
        map.put("count",countList);
        return Result.success(map);
    }


    @GetMapping("/pieData")
    public Result getPieData() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Student> employeeList = studentService.selectAll(null);
        Set<String> departmentNameSet = employeeList.stream().map(Student::getMajorin).collect(Collectors.toSet());
        for (String departmentName : departmentNameSet) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", departmentName);
            // 统计这个部门下面的员工的数量
            long count = employeeList.stream().filter(employee -> employee.getMajorin().equals(departmentName)).count();
            map.put("value", count);
            list.add(map);
        }
        return Result.success(list);
    }
}
