<script setup lang="ts">
</script>

<template>
  <el-scrollbar>
    <el-space direction="vertical" fill>
      <slot></slot>
    </el-space>
  </el-scrollbar>
</template>

<style scoped>
:deep(.el-space__item) {
  width: 100%;
}

:deep(.el-space) {
  width: 100%;
}
</style>
