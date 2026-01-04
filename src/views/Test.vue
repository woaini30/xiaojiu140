<template>
  <div>
    <div style="margin-bottom: 20px">
      <el-button type="primary" @click="router.back()">返回主页</el-button>
    </div>


    <div style="margin-bottom: 20px">
      <el-input clearable v-model="data.input" style="width: 240px" placeholder="请输入内容" :prefix-icon="Search" /> {{data.input}}
      <el-input style="width: 200px" :suffix-icon="Calendar"></el-input>
      <el-input type="textarea" v-model="data.descr" style="width: 300px" placeholder="请输入一段描述"></el-input>
    </div>

    <div style="margin: 20px 0">
      <el-select
          clearable
          multiple
          value-key="id"
          v-model="data.value"
          placeholder="请选择水果"
          style="width: 240px"
      >
        <el-option
            v-for="item in data.options"
            :key="item.id"
            :label="item.label"
            :value="item.name"
        />
      </el-select>
    </div>

    <div style="margin: 20px 0">
      <el-radio-group v-model="data.sex">
        <el-radio value="男">男</el-radio>
        <el-radio value="女">女</el-radio>
      </el-radio-group> <span style="margin-left: 50px">{{data.sex}}</span>
    </div>

  </div>
</template>

<script setup>
import {reactive} from "vue";
import {Calendar, Search} from "@element-plus/icons-vue"
import router from "@/router/index.js";

const data = reactive(  {
  input: 'test页面',
  descr: '',
  value: '',
  options: [{id: 1, name: '苹果'}, {id: 2, name: '香蕉'}, {id: 3,name: '桃子'}, {id: 4, name: '苹果'},],
  sex: '男',
})
</script>