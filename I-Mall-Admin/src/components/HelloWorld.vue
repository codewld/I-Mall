<script setup lang="ts">
import { ref } from 'vue';
import { rHello } from '@/api/hello'
import { ElMessage } from 'element-plus';

let msg = ref('Hello World')

const hello = () => {
  rHello().then(res => {
    ElMessage.success(res)
  }).catch(err => {
    ElMessage.warning(err)
  })
}
</script>

<template>
  <h1>{{ msg }}</h1>
  <el-button type="primary" @click="hello">Primary</el-button>
</template>
