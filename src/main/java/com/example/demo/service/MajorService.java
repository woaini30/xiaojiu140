package com.example.demo.service;

import com.example.demo.entity.Major;

import com.example.demo.mapper.MajorMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {

    @Resource
    private MajorMapper majorMapper;
    public List<Major> selectAll(Major major) {
        return majorMapper.selectAll(major);
    }

    public Major selectById(Integer id) {
        return majorMapper.selectById(id);
    }
    public PageInfo<Major> selectPage( Major major,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Major> list = majorMapper.selectAll(major);
        return PageInfo.of(list);
    }
    public void add(Major major) {
        majorMapper.insert(major);
    }

    public void update(Major major) {majorMapper.updateById(major);
    }

    public void deleteById(Integer id) {
        majorMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.deleteById(id);
        }
    }
}
