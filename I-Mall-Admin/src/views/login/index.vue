<script setup lang="ts">
import { Ref, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { rLogin } from '@/api/account'
import { useJWTStore } from '@/store';
import { useRouter } from 'vue-router';

const router = useRouter();

/**
 * 登录表单数据
 */
const loginFormData: Ref<Account.loginParam> = ref({
  username: '',
  password: ''
})

/**
 * 登录
 */
const login = () => {
  rLogin(loginFormData).then(res => {
    const JWTStore = useJWTStore()
    JWTStore.set(res)
    router.push({ name: 'home' })
    ElMessage.success('登录成功')
  }).catch(err => {
    ElMessage.warning(err)
  })
}
</script>

<template>
  <div class="h-full flex justify-center items-center bg-login bg-center bg-fixed bg-no-repeat bg-cover text-center">
    <div
        class="w-112 h-112 py-6 px-8 border-solid border border-transparent rounded-3xl space-y-8 bg-white bg-opacity-95">
      <h1 class="mt-10 text-6xl font-bold tracking-wider select-none">I-Mall</h1>
      <el-form label-position="top" :model="loginFormData" ref="loginForm">
        <el-form-item label="账号：" prop="username">
          <el-input type="text" v-model.trim="loginFormData.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input type="password" v-model.trim="loginFormData.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="mt-6">
          <el-button type="primary" class="w-full" @click="login">立即登录</el-button>
        </el-form-item>
      </el-form>
      <div>
        <label class="text-gray-400">没有账号？</label>
        <router-link to="/register" class="text-blue-500 hover:text-blue-800">注册新账号</router-link>
      </div>
    </div>
  </div>
</template>
