package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Major;
import com.example.demo.service.MajorService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/major")
public class MajorController {

    @Resource
    private MajorService majorService;
    /**
     *查询所有数据
     */
    @GetMapping("/selectAll")
    public Result selectAll(Major major) {
        List<Major> list = majorService.selectAll(major);
        return Result.success(list);
    }
    /**
     *通过id查询数据
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Major major = majorService.selectById(id);
        return Result.success(major);
    }
    /**
     *分页处理
     */
    @GetMapping("/selectPage")
    public Result selectPage(Major major,
            @RequestParam(defaultValue = "1") Integer pageNum ,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<Major> pageInfo = majorService.selectPage(major,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    /**
     *添加单个数据
     */
    @PostMapping("/add")
    public Result add(@RequestBody Major major) {
       majorService.add(major);
        return Result.success();
    }
    /**
     *添更新数据
     */
    @PutMapping("/update")
    public Result update(@RequestBody Major major) {
        majorService.update(major);
        return Result.success();
    }
    /**
     *删除单个数据
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        majorService.deleteById(id);
        return Result.success();
    }
    /**
     *批量删除数据
     * */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        majorService.deleteBatch(ids);
        return Result.success();
    }
}
