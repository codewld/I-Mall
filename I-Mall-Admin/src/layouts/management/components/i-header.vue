<script setup lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import 'element-plus/es/components/message-box/style/css';
import { useRouter } from 'vue-router';
import useAccount from '@/composables/useAccount';

const router = useRouter();

const { reset } = useAccount();

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
        reset()
            .then(() => {
              ElMessage.success('退出成功')
            })
            .catch(() => {
              ElMessage.error('退出失败')
            })
      })
      .catch(() => {
      })
}
</script>

<template>
  <div
      class="h-full px-6 z-10 flex justify-between items-center border-0 border-b border-gray-300 border-solid select-none">
    <h1 @click="router.push({name: 'home'})" class="text-4xl font-bold tracking-wider cursor-pointer">
      I-Mall
    </h1>
    <div class="space-x-4">
      <el-link href="https://codewld.github.io/I-Mall-Docs/" target="_blank" type="success">项目文档</el-link>
      <el-link href="https://github.com/codewld/I-Mall" target="_blank" type="primary">开源地址</el-link>
      <el-button type="text" class="text-red-500" @click="handleLogout">退出登录</el-button>
    </div>
  </div>
</template>
