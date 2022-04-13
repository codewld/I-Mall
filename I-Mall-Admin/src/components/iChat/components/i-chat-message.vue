<script setup lang="ts">
import { PropType } from 'vue';
import { isEqual } from '@/utils/chatUtil';


defineProps({
  message: {
    type: Object as PropType<Chat.msg>,
    required: true
  }
})
</script>

<template>
  <div class="w-full my-2 flex items-center" :class="[isEqual(message.sender) ? 'justify-end' : 'justify-start']">
    <div class="p-1 box-border bg-white rounded-md border text-left text-sm" style="max-width: 66.666667%">
      {{message.msg}}
    </div>
  </div>
</template>
