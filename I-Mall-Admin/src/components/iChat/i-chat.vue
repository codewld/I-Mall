<script setup lang="ts">
import { Ref, ref } from 'vue';
import { ChatDotSquare, Close } from '@element-plus/icons-vue';
import IChatContacts from '@/components/iChat/components/i-chat-contacts.vue';
import IChatMessages from '@/components/iChat/components/i-chat-messages.vue';
import 'element-plus/es/components/input/style/css';
import 'element-plus/es/components/scrollbar/style/css';
import { useChat } from '@/components/iChat/composables/useChat';


// -- 聊天面板相关 --
/**
 * 是否展示聊天面板
 */
const isShowPanel = ref(false)

/**
 * 处理聊天面板的打开与关闭
 */
const triggerPanel = () => {
  isShowPanel.value = !isShowPanel.value
  currentContact.value = undefined
  editingMsg.value = ''
  setActiveStatus(isShowPanel.value)
}


// -- 会话相关 --
/**
 * 正在编辑中的消息
 */
const editingMsg: Ref<string> = ref('')

/**
 * 发送消息
 */
const sendMsg = () => {
  sendCommunicateMsg(editingMsg.value)
  editingMsg.value = ''
}


// -- 聊天通信相关 --
const {
  setActiveStatus,
  currentContact,
  establishSession,
  sendCommunicateMsg,
  sessionMsg,
  unreadCount,
  contacts
} = useChat()
</script>

<template>
  <div class="z-10 fixed right-5 bottom-5">
    <!--聊天按钮-->
    <el-badge :value="unreadCount" :max="99" :hidden="unreadCount === 0 || isShowPanel"
              class="absolute bottom-0 right-0">
      <div class="p-3 box-border flex items-center justify-center rounded-3xl cursor-pointer shadow"
           :class="{'bg-blue-400': !isShowPanel, 'bg-blue-200': isShowPanel}"
           @click="triggerPanel">
        <el-icon :size="20" color="#FFFFFF">
          <chat-dot-square/>
        </el-icon>
      </div>
    </el-badge>
    <!--聊天面板-->
    <transition name="el-fade-in-linear">
      <div v-if="isShowPanel"
           class="absolute bottom-10 right-10 shadow-2xl w-112 h-112 flex flex-col py-4 px-4 bg-white bg-opacity-95 rounded">
        <!--面板头-->
        <div class="h-10 flex justify-end">
          <el-icon :size="24" class="cursor-pointer" @click="triggerPanel">
            <close/>
          </el-icon>
        </div>
        <!--面板区-->
        <div class="h-full flex overflow-hidden space-x-2">
          <!--联系人列表-->
          <div class="w-1/3 h-full">
            <i-chat-contacts :contacts="contacts" :current-contact="currentContact" @selectContact="establishSession"/>
          </div>
          <!--聊天区-->
          <div class="w-2/3 flex flex-col space-y-2">
            <template v-if="currentContact">
              <!--消息区-->
              <div class="h-4/6 flex-none">
                <i-chat-messages :messages="sessionMsg"/>
              </div>
              <!--输入区-->
              <div class="h-2/6 relative">
                <el-input v-model="editingMsg" type="textarea" resize="none" placeholder="请输入" maxlength="100"
                          show-word-limit class="h-full "/>
                <el-button size="small" class="absolute right-2 bottom-2 w-1/4 mt-1" @click="sendMsg">发送</el-button>
              </div>
            </template>
            <el-empty v-else description="请选择联系人"/>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<style scoped>
:deep(.el-badge__content--danger) {
  @apply bg-blue-600;
  @apply select-none;
  transform: translateY(-50%) translateX(100%) scale(0.8);
}

:deep(.el-textarea__inner) {
  @apply h-full;
  @apply rounded;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color-base)) inset;
}

:deep(.el-textarea .el-input__count) {
  @apply left-3;
  @apply bottom-2;
}
</style>
