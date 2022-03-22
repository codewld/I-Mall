<script setup lang="ts">
import { ref } from 'vue';

const isChoose = ref(false)
</script>

<template>
  <div class="p-1.5 pr-2.5 box-border truncate bg-gray-100 hover:bg-gray-200" :class="{'bg-gray-300': isChoose}"
       @click="isChoose = true">
    <div class="flex justify-between items-center">
      <p class="text-sm">
        小张
      </p>
      <span class="text-xs text-gray-500">昨天</span>
    </div>
    <span class="text-xs text-gray-500">
      你好呀！上次说的事情还记得吗？
    </span>
  </div>
</template>
