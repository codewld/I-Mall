<script setup lang="ts">
import { useJWTStore } from '@/store';
import { ElMessage, ElMessageBox } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import 'element-plus/es/components/message-box/style/css';
import { useRouter } from 'vue-router';

const router = useRouter();

/**
 * 退出登录确认
 */
const handleLogout = () => {
  ElMessageBox.confirm(
      '是否要退出登录?',
      '提醒',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }
  )
      .then(() => {
        logout()
      })
      .catch(() => {
      })
}

/**
 * 退出登录
 */
const logout = () => {
  const JWTStore = useJWTStore()
  JWTStore.$reset()
  ElMessage.success('退出成功')
  router.replace({ name: 'login' })
}
</script>

<template>
  <div
      class="h-full px-6 z-10 flex justify-between items-center border-0 border-b border-gray-300 border-solid select-none">
    <h1 @click="router.push({name: 'home'})" class="text-4xl font-bold tracking-wider cursor-pointer">
      I-Mall
    </h1>
    <div>
      <el-button type="text" class="text-red-500" @click="handleLogout">退出登录</el-button>
    </div>
  </div>
</template>
