<template>
  <div>
    <div class="card" style="margin-bottom: 5px;background-color: rgb(255,255,255)" >
      <el-input style="width: 240px; margin-right: 10px" v-model="data.no" placeholder="请输入学号查询">
        <template #prefix><el-icon><Search/></el-icon></template></el-input>
      <el-button type="primary"@click="load">查 询</el-button>
      <el-button type="warning"@click="reset">重 置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px;background-color: rgba(255,255,255,0.74)">
      <el-button type="primary"@click="handleAdd">新 增</el-button>
      <el-button type="danger" @click="delBatch">批量删除</el-button>
      <el-upload
          style="display: inline-block;margin: 0 20px"
          action="http://localhost:8081/student/import"
          show-file-list="false"
          :on-success="importSuccess"
      >
        <el-button type="info">导入</el-button>
      </el-upload>
      <el-button type="success" @click="exportDate">导出</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="60" />
        <el-table-column label="账号" prop="username" />
        <el-table-column label="姓名" prop="name" />
        <el-table-column label="性别" prop="sex" />
        <el-table-column label="学号" prop="no" />
        <el-table-column label="年龄" prop="age" />
        <el-table-column label="专业" prop="majorin" />
        <el-table-column label="头像" >
          <template #default="scope">
            <img v-if="scope.row.avatar" :src="scope.row.avatar"  alt="" style="display: block; width: 40px;height: 40px;border-radius: 50%">
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="handleUpdate (scope.row)" type="primary" :icon="Edit" circle></el-button>
            <el-button @click="del(scope.row.id)" type="primary" :icon="Delete" circle color="red"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top: 10px">
        <el-pagination
            @size-change="load"
            @current-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total"
        />
      </div>
    </div>

    <el-dialog title="学生信息" v-model="data.formVisible"  width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form"  label-width="80px" style="padding-right: 50px;padding-top:  10px">
        <el-form-item label="账号" prop="username">
          <el-input  v-model="data.form.username" autocomplete="off" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="专业">
          <el-select style="width: 100%" v-model="data.form.majorin">
            <el-option v-for="item in data.majorList" :key="item.id" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8081/files/upload"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item  label="姓名" prop="name">
          <el-input :disabled="data.form.id" v-model="data.form.name" autocomplete="off" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="性别">
         <el-radio-group v-model="data.form.sex">
           <el-radio value="男" label="男"></el-radio>
           <el-radio value="女" label="女"></el-radio>
         </el-radio-group>
        </el-form-item>
        <el-form-item  label="学号" prop="no">
          <el-input :disabled="data.form.id" v-model="data.form.no" autocomplete="off" placeholder="请输入学号"/>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number style="width: 180px" :min="10" v-model="data.form.age" autocomplete="off" placeholder="请输入年龄"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </template>
    </el-dialog>


  </div>
</template>

<script setup>
import { reactive,ref } from "vue";
import {Edit, Delete, Search, Plus} from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
  name: null,
  tableData: [],
  pageNum: 1,
  pageSize: 5,
  total: 0,
  no: null,
  formVisible: false,
  form: {},
  ids: [],
  majorList: [],
  rules: {
    username: [
      {required: true,message: '请输入账号', trigger: 'blur'}
    ],
    name: [
      {required: true,message: '请输入姓名', trigger: 'blur'}
    ],
    no: [
      {required: true,message: '请输入学号', trigger: 'blur'}
    ]
  }
})

const formRef = ref()

request.get('/major/selectAll').then(res => {
  data.majorList = res.data
})

const exportDate = () =>{
  window.open('http://localhost:8081/student/export')
}

const importSuccess = (res) =>{
  if (res.code === '200'){
    ElMessage.success('导入成功')
    load()
  }else {
    ElMessage.error(res.msg)
  }
}

const load = () => {
  request.get('/student/selectPage',{
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      no: data.no,
    }
  }).then(res => {
    console.log()
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()

const reset = () => {
  data.no = null
  load()
}

const handleAdd = () => {
  data.formVisible = true
  data.form = {}
}

const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.form ["id"] ? update(): add()
    }
  })
}

const add = () => {
  request.post('/student/add',data.form).then(res => {
    if(res.code === '200') {
      data.formVisible = false
      ElMessage.success('新增成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleAvatarSuccess = (res) => {
  console.log(res.data)
  data.form.avatar = res.data
}

const update = (id) => {
  request.put('/student/update',data.form).then(res => {
    if(res.code === '200') {
      data.formVisible = false
      ElMessage.success('更新成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleUpdate = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const del = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？','删除确认', { type:'warning'}).then(() =>{
    request.delete('/student/deleteById/' +id).then(res =>{
      if(res.code === '200') {
        ElMessage.success('单个删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}

const handleSelectionChange = (rows) =>{
  console.log(rows)
  data.ids = rows.map(row => row.id)
  console.log(data.ids)
}

const delBatch = () => {
  if (data.ids.length === 0) {
    ElMessage.warning('请选择数据')
    return
  }
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？', '删除确认', {type: 'warning'}).then(() => {
    request.delete('/student/deleteBatch', {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('批量删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}
</script>