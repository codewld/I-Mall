<script setup lang="ts">
import { Check, Close } from '@element-plus/icons-vue';

const props = defineProps({
  /**
   * 状态
   */
  status: {
    type: Boolean,
    default: true
  }
})
</script>

<template>
  <div class="flex items-center justify-center">
    <el-icon :size="20" :color="status ? 'green' : 'red'">
      <Check v-if="status"/>
      <Close v-else/>
    </el-icon>
  </div>
</template>
