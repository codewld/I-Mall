<script setup lang="ts">
import { ref } from 'vue';
import { rHello } from '@/api/hello'
import { ElMessage } from 'element-plus';

let msg = ref('Hello World!')

const hello = () => {
  rHello().then(res => {
    ElMessage.success(res)
  }).catch(err => {
    ElMessage.warning(err)
  })
}
</script>

<template>
  <div class="h-full relative flex flex-col justify-center items-center">
    <p class="text-9xl font-bold tracking-wider italic select-none hover:text-blue-400">{{ msg }}</p>
    <el-button @click="hello" class="m-20">发个请求试试</el-button>
  </div>
</template>
