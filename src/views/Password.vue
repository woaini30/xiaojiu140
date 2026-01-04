<template>
<div class="card" style="width: 40% ;padding: 50px 50px; background-color: #ffffff;place-self: center">
  <el-form ref="formRef" :rules="data.rules" :model="data.form"  label-width="100px" style="padding-right: 50px;padding-top:  10px">
    <el-form-item label="原密码" prop="password">
      <el-input show-password v-model="data.form.password" autocomplete="off" placeholder="请输入原密码"/>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input show-password v-model="data.form.newPassword" autocomplete="off" placeholder="请输入新密码"/>
    </el-form-item>
    <el-form-item label="确认新密码" prop="confirmPassword" required>
      <el-input show-password v-model="data.form.confirmPassword" autocomplete="off" placeholder="请再次确认新密码"/>
    </el-form-item>
  </el-form>
  <div style="text-align: center">
    <el-button @click="updatePassword" type="primary" style="padding: 20px">立即修改</el-button>
  </div>
</div>
</template>

<script setup>
import {reactive,ref} from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request.js";

const validatePass = (rule, value, callback) => {
  if (!value){
    callback(new Error('请再次确认密码'))
  }else if (value !== data.form.newPassword) {
    callback(new Error("两次输入的密码不一致"))
  }else {
    callback()
  }
}

const updatePassword = () => {
  data.form.id=data.user.id
  data.form.role = data.user.role
  formRef.value.validate((valid) =>{
    if (valid){
      request.put('/updatePassword',data.form).then(res =>{
        if (res.code === '200') {
          ElMessage.success('修改成功')
          localStorage.removeItem('demo-user')
          setTimeout(() => {
            location.href='/login'
          },500)
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
const formRef = ref()
const data = reactive({
  user: JSON.parse(localStorage.getItem('demo-user')),
  form: {},
  rules: {
    password: [
      {required: true, message: '请输入原密码', trigger: 'blur'}
    ],
    newPassword: [
      {required: true, message: '请输入新密码', trigger: 'blur'}
    ],
    confirmPassword: [
      {validator: validatePass, trigger: 'blur'}
    ],
  }
})
</script>