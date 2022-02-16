<script setup lang="ts">
import IHeader from './components/i-header.vue';
import ISide from './components/i-side.vue';
</script>

<template>
  <el-container class="h-full">
    <el-header class="p-0">
      <i-header/>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <i-side/>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
