<template>
  <div class="login-container">

    <div class="login-box" >
      <div style="padding: 50px 100px; background-color: hotpink;box-shadow: 0 0 10px rgba(0,0,0.1)">
        <el-form style="width: 500px" >
          <div style="margin-bottom: 20px; font-size: 50px;text-align: center; color: darkblue;font-weight: bold">
            欢迎登录后台管理系统</div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container{
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/beijing.jpg");
  background-size: cover;
}
.login-box{
  width: 50%;
  height: 100%;
  align-items: center;
  display: flex;
  left: 500px;
  position: absolute;
}
</style>
<script setup lang="ts">
import {Lock, Search, User} from "@element-plus/icons-vue";
</script>