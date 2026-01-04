package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;
    /**
     *查询所有数据
     */
    @GetMapping("/selectAll")
    public Result selectAll(Admin admin) {
        List<Admin> list = adminService.selectAll(admin);
        return Result.success(list);
    }
    /**
     *通过id查询数据
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }
    /**
     *分页处理
     */
    @GetMapping("/selectPage")
    public Result selectPage(Admin admin,
            @RequestParam(defaultValue = "1") Integer pageNum ,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<Admin> pageInfo = adminService.selectPage(admin,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    /**
     *添加单个数据
     */
    @PostMapping("/add")
    public Result add(@RequestBody Admin admin) {
       adminService.add(admin);
        return Result.success();
    }
    /**
     *添更新数据
     */
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }
    /**
     *删除单个数据
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }
    /**
     *批量删除数据
     * */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        adminService.deleteBatch(ids);
        return Result.success();
    }
}
