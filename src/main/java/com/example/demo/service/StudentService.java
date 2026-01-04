package com.example.demo.service;

import cn.hutool.core.util.StrUtil;
import com.example.demo.entity.Account;
import com.example.demo.entity.Student;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;
    public List<Student> selectAll(Student student) {
        return studentMapper.selectAll(student);
    }

    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }
    public PageInfo<Student> selectPage( Student student,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> list = studentMapper.selectAll(student);
        return PageInfo.of(list);
    }
    public void add(Student student) {
        String username = student.getUsername();
        Student dbStudent =studentMapper.selectByUsername(username);
        if (dbStudent !=null){
            throw new CustomException("500", "账号已存在，请更换别的账号");
        }
        Student dbStudent1 =studentMapper.selectByNo(student.getNo());
        if (dbStudent1 !=null){
            throw new CustomException("500", "学号已存在，请更换别的账号");
        }
        if (StrUtil.isBlank(student.getPassword())) {
            student.setPassword("123456");
        }
        if (StrUtil.isBlank(student.getUsername())) {
            student.setName(student.getUsername());
        }
            student.setRole("STU");
        studentMapper.insert(student);
    }

    public void update(Student student) {studentMapper.updateById(student);
    }

    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.deleteById(id);
        }
    }

    public Student login(Account account) {
        String username = account.getUsername();
        Student dbStudent =studentMapper.selectByUsername(username);
        if(dbStudent==null){
            throw new CustomException("500","账号不存在");
        }
        String password = account.getPassword();
        if (!dbStudent.getPassword().equals(password)){
            throw new CustomException("500","账号或密码错误");
        }
        return dbStudent;
    }

    public void register(Student student) {
        this.add(student);
    }

    public void updatePassword(Account account) {
        Integer id =  account.getId();
        Student student = this.selectById(id);
        if (!student.getPassword().equals(account.getPassword())) {
            throw new CustomException("500","对不起，原密码错误");
        }
        student.setPassword(account.getNewPassword());
        this.update(student);
    }
}
