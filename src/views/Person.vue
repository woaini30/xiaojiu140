<template>
  <div class="card" style="width: 40% ;padding: 20px 50px; background-color: #ffffff;place-self: center" >
    <el-form ref="formRef" :rules="data.rules" :model="data.form"  label-width="80px" style="padding-right: 50px;padding-top:  10px">
          <div style="width: 110%; display: flex;justify-content: center;margin-bottom: 20px">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8081/files/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </div>
      <el-form-item label="账号" prop="username">
        <el-input v-model="data.form.username" autocomplete="off" placeholder="请输入账号"/>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input disabled v-model="data.form.name" autocomplete="off" placeholder="请输入姓名"/>
      </el-form-item>
      <div v-if="data.user.role === 'STU'">
        <el-form-item label="性别">
          <el-radio-group v-model="data.form.sex">
            <el-radio value="男" label="男"></el-radio>
            <el-radio value="女" label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="学号" prop="no">
          <el-input disabled v-model="data.form.no" autocomplete="off" placeholder="请输入学号"/>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number style="width: 180px" :min="10" v-model="data.form.age" autocomplete="off" placeholder="请输入年龄"/>
        </el-form-item>
      </div>
      <div style="text-align: center">
        <el-button @click="updateUser" type="primary" style="padding: 20px">更新个人信息</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import {Plus} from "@element-plus/icons-vue";

const formRef = ref()
const data = reactive({
  user: JSON.parse(localStorage.getItem('demo-user')),
  form: {},
  rules: {
    username: [
      {required: true,message: '请输入账号', trigger: 'blur'}
    ],
    name: [
      {required: true,message: '请输入姓名', trigger: 'blur'}
    ],
    no: [
      {required: true,message: '请输入姓名', trigger: 'blur'}
    ],
  }
})

const handleAvatarSuccess = (res) => {
  console.log(res.data)
  data.form.avatar = res.data
}

const emit = defineEmits(['updateUser'])

if (data.user.role === 'STU') {
  request.get('/student/selectById/' + data.user.id).then(res => {
    data.form = res.data
  })
}else {
  data.form = data.user
}

const updateUser =() => {
  if(data.user.role === 'STU'){
    request.put('/student/update', data.form).then(res => {
      if (res.code=== '200'){
        ElMessage.success('更新成功')
        localStorage.setItem('demo-user',JSON.stringify(data.form))
        emit("updateUser")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
  else {
    request.put('/admin/update', data.form).then(res => {
      if (res.code=== '200'){
        ElMessage.success('更新成功')
        localStorage.setItem('demo-user',JSON.stringify(data.form))
        emit("updateUser")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }

}
</script>


<style scoped>
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>


<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
}
</style>

