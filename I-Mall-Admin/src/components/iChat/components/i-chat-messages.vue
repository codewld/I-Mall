<script setup lang="ts">
import { PropType } from 'vue';
import IChatMessage from './i-chat-message.vue';


defineProps({
  messages: {
    type: Object as PropType<Chat.msg[]>,
    required: true
  }
})
</script>

<template>
  <el-scrollbar class="h-4/6 w-full p-3 box-border border rounded bg-gray-50">
    <i-chat-message v-for="message in messages" :key="message" :message="message"></i-chat-message>
  </el-scrollbar>
</template>

<style scoped>
:deep(.el-scrollbar__view) {
  @apply h-full;
}
</style>
