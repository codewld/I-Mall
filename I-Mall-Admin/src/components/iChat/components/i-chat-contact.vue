<script setup lang="ts">
import { PropType } from 'vue';

defineProps({
  /**
   * 个人信息
   */
  personInfo: Object as PropType<Chat.user>,
  /**
   * 是否被选中
   */
  isChoose: Boolean
})
</script>

<template>
  <div class="p-1.5 pr-2.5 box-border truncate bg-gray-100 hover:bg-gray-200" :class="{'bg-gray-300': isChoose}">
    <div class="flex justify-between items-center">
      <div class="flex items-center overflow-hidden">
        <p class="flex-shrink-0 mr-0.5 w-4 h-4 scale-90 bg-gray-400 text-center text-xs text-white">
          {{ personInfo.system.slice(0, 1) }}
        </p>
        <p class="text-sm truncate">{{ personInfo.id }}</p>
      </div>
      <p class="flex-shrink-0 w-5 text-xs text-gray-500">昨天</p>
    </div>
    <span class="text-xs text-gray-500">
      你好呀！上次说的事情还记得吗？
    </span>
  </div>
</template>
