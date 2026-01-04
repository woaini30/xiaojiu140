<template xmlns="http://www.w3.org/1999/html">
  <div>
    <!--  头部开始  -->
    <div style="height: 50px; background-color: cyan; display: flex; align-items: center">
      <div style="width: 400px; display: flex; align-items: center">
      <img style="width: 30px; padding-left: 10px" src="@/assets/logo.jpg" alt="">
      <span style="font-size: 30px;color: coral">后台管理系统</span>
    </div>
      <div style="flex: 1"></div>
      <div style="width: fit-content; display: flex; align-items: center; padding-right: 50px">
        <img :src="data.user.avatar" alt="" style="width: 40px;height: 40px; border-radius: 50%">
        <span style="color: black; margin-left: 10px">{{ data.user.name }}</span>
      </div>


    </div>
  <!-- 头部结束 -->

  <!-- 下面部分开始 -->
  <div style="display: flex">
    <!-- 左侧导航菜单开始 -->
    <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
      <el-menu router :default-active="router.currentRoute.value.path"  :default-openeds="['1']" style="border: 0">
        <el-menu-item index="/manager/home">
          <el-icon><House/></el-icon>
          系统首页</el-menu-item>
        <el-menu-item index="/manager/data" v-if="data.user.role === 'ADMIN'">
          <el-icon><DataAnalysis/></el-icon>
          数据统计</el-menu-item>
        <el-menu-item index="/manager/article" v-if="data.user.role === 'ADMIN'">
          <el-icon><Document/></el-icon>
          文章管理</el-menu-item>
        <el-menu-item index="/manager/major" v-if="data.user.role === 'ADMIN'">
          <el-icon><OfficeBuilding/></el-icon>
          专业管理</el-menu-item>
        <el-sub-menu index="1" v-if="data.user.role === 'ADMIN'">
          <template #title>
            <span>
              <el-icon><User/></el-icon>
              用户管理</span>
          </template>
          <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
          <el-menu-item index="/manager/student">学生信息</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/manager/person"><el-icon><UserFilled/></el-icon>个人信息</el-menu-item>
        <el-menu-item index="/manager/password"><el-icon><Lock/></el-icon>修改密码</el-menu-item>
        <el-menu-item @click="logout"><el-icon><SwitchButton/></el-icon>退出登录</el-menu-item>
      </el-menu>
    </div>
    <!-- 左侧导航菜单结束 -->

    <!-- 右侧主体区域开始 -->
    <div style="flex: 1; width: 0; background-color: #f5f7ff">
      <RouterView  @updateUser="updateUser"/>
    </div>
    <!-- 右侧主体区域结束 -->
  </div>
  <!-- 下面部分结束 -->
  </div>
</template>
<script setup>
import router from "@/router/index.js";
import {
  DataAnalysis,
  Document,
  House,
  Lock,
  OfficeBuilding,
  SwitchButton,
  User,
  UserFilled
} from "@element-plus/icons-vue";
import {reactive} from "vue";


const logout = () => {
  localStorage.removeItem('demo-user')
  location.href = '/login'
}

const data = reactive({
  user: JSON.parse(localStorage.getItem('demo-user'))
})

const updateUser =() => {
  data.user = JSON.parse(localStorage.getItem('demo-user'))
}
</script>

<style>
.el-menu .is-active {
  background-color: rgba(255, 145, 0, 0.24) !important;
}
.el-sub-menu__title {
  background-color: rgb(255, 255, 255) !important;
}

</style>