<script setup lang="ts">
import { ref } from 'vue';
import { ChatDotSquare, Close } from '@element-plus/icons-vue';
import IChatPerson from '@/components/iChat/components/i-chat-person.vue';
import 'element-plus/es/components/input/style/css';
import 'element-plus/es/components/scrollbar/style/css';
import { useWebSocket } from '@/webSocket';


const { send } = useWebSocket()


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
  let webSocketMsg: Websocket.webSocketMsg = {
    type: 'triggerPanel',
    data: {
      active: isShowPanel.value
    }
  }
  send(webSocketMsg)
}


// -- 会话相关 --
/**
 * 当前联系人
 */
const contact = ref()

/**
 * 选择联系人
 */
const chooseContact = (i: number) => {
  contact.value = i
  let webSocketMsg: Websocket.webSocketMsg = {
    type: 'chooseContact',
    data: {
      system: 'WEB',
      id: i
    }
  }
  send(webSocketMsg)
}

/**
 * 正在编辑中的消息
 */
const msg = ref()
</script>

<template>
  <div class="z-10 fixed right-5 bottom-5">
    <!--聊天按钮-->
    <el-badge is-dot :hidden="isShowPanel" class="absolute bottom-0 right-0">
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
          <!--左侧-联系人列表-->
          <el-scrollbar class="w-1/3 h-full rounded">
            <template v-for="i in 90">
              <i-chat-person @click="chooseContact(i)" :is-choose="contact === i"/>
            </template>
          </el-scrollbar>
          <!--右侧-聊天区-->
          <div class="w-2/3 flex flex-col space-y-2">
            <template v-if="contact">
              <!--消息区-->
              <el-scrollbar class="h-4/6 p-2 box-border border rounded bg-gray-50">
                <template v-for="i in 90">
                  <P>123</P>
                </template>
              </el-scrollbar>
              <!--输入区-->
              <div class="h-2/6 relative">
                <el-input v-model="msg" type="textarea" resize="none" placeholder="请输入" maxlength="100"
                          show-word-limit class="h-full "/>
                <el-button size="small" class="absolute right-2 bottom-2 w-1/4 mt-1">发送</el-button>
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
}

:deep(.el-scrollbar__view) {
  @apply h-full;
}

:deep(::-webkit-scrollbar) {
  width: 6px;
}

:deep(::-webkit-scrollbar-thumb) {
  background-color: #0003;
  border-radius: 10px;
  transition: all .2s ease-in-out;
}

:deep(::-webkit-scrollbar-track) {
  border-radius: 10px;
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
