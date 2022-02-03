<script setup lang="ts">
import { ref } from 'vue';

let username = ref('admin')
</script>

<template>
  Login
  <el-input v-model="username" placeholder="Please input"/>
</template>
