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
  <el-scrollbar class="bg-gray-50 border rounded">
    <div class="py-1 px-2.5 box-border">
      <i-chat-message v-for="message in messages" :key="message" :message="message"/>
    </div>
  </el-scrollbar>
</template>

