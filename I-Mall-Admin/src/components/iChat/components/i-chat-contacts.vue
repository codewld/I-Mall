<script setup lang="ts">
import { PropType } from 'vue';
import IChatContact from './i-chat-contact.vue';
import { isSame } from '@/utils/objUtil';


defineProps({
  contacts: {
    type: Object as PropType<Chat.user[]>,
    required: true
  },
  currentContact: {
    type: Object as PropType<Chat.user>,
    required: true
  }
})


const emits = defineEmits(['selectContact']);
</script>

<template>
  <el-scrollbar class="rounded">
      <i-chat-contact v-for="contact in contacts" :key="contact"
                      :person-info="contact"
                      :is-choose="isSame(currentContact, contact)"
                      @click="emits('selectContact', contact)">
      </i-chat-contact>
  </el-scrollbar>
</template>
