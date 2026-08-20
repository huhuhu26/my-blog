<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { registerApi } from '@/api/register.js'
import { ElMessage } from 'element-plus'

const router = useRouter()

const username = ref('')
const password = ref('')


const register = async () => {

  // 简单非空校验
  if (!username || !password) {
    alert('用户名和密码不能为空')
    return
  }

  try {
    const res = await registerApi({username: username.value, password : password.value});

    if (res.code === 200) {
      // 把 token 存起来
      ElMessage.success('注册成功')
      router.push('/login')
    } else {
      alert(res.data.message)
    }
  } catch (error) {
    ElMessage.error('注册失败')
  }
}
</script>

<template>
<div class="container">
    <div class="login-form">
      <el-form label-width="80px" style="margin-right: 40px;"> 
        <p class="title">博客系统</p>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="username" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button class="button" type="primary" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.container { 
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}
.login-form {
  max-width: 400px;
  padding: 30px;
  margin-right: 30px;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  background-color: white;
}

.title {
  font-size: 30px;
  font-family: '楷体';
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
}

.button {
  margin-top: 30px;
  width: 120px;
}
</style>
