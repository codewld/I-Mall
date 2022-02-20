<script setup lang="ts">
</script>

<template>
  <div class="scrollbar-container">
    <el-scrollbar>
      <el-space direction="vertical" fill>
        <slot></slot>
      </el-space>
    </el-scrollbar>
  </div>
</template>

<style scoped>
:deep(.el-space__item) {
  width: 100%;
}

:deep(.el-space) {
  width: 100%;
}

.scrollbar-container {
  position: absolute;
  top: var(--el-main-padding);
  bottom: var(--el-main-padding);
  left: var(--el-main-padding);
  right: var(--el-main-padding);
}
</style>
