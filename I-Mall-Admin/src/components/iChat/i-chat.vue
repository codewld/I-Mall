<script setup lang="ts">
import { ref } from 'vue';
import { ChatDotSquare, Close } from '@element-plus/icons-vue';
import IChatPerson from '@/components/iChat/components/i-chat-person.vue';
import 'element-plus/es/components/input/style/css';
import 'element-plus/es/components/scrollbar/style/css';


/**
 * 是否展示聊天面板
 */
const isShowPanel = ref(false)

/**
 * 正在编辑中的消息
 */
const msg = ref()
</script>

<template>
  <div class="z-10">
    <!--聊天按钮-->
    <el-badge is-dot class="absolute bottom-0 right-0">
      <div class="p-3 box-border flex items-center justify-center rounded-3xl bg-blue-400 cursor-pointer shadow"
           @click="isShowPanel=!isShowPanel">
        <el-icon :size="20" color="#FFFFFF">
          <chat-dot-square/>
        </el-icon>
      </div>
    </el-badge>
    <!--聊天面板-->
    <transition name="el-fade-in-linear">
      <div v-if="isShowPanel"
           class="absolute bottom-10 right-10 shadow-2xl w-112 h-112 flex flex-col py-4 px-4 bg-white bg-opacity-90 rounded">
        <!--面板头-->
        <div class="h-10 flex justify-end">
          <el-icon :size="24" class="cursor-pointer" @click="isShowPanel=false">
            <close/>
          </el-icon>
        </div>
        <!--面板区-->
        <div class="h-full flex overflow-hidden space-x-2">
          <el-scrollbar class="w-1/3 h-full rounded-l">
            <template v-for="() in 90">
              <i-chat-person/>
            </template>
          </el-scrollbar>
          <el-scrollbar class="w-2/3 rounded-r">
            <div class="h-full flex flex-col space-y-2">
              <div class="h-4/6 p-2 box-border border rounded bg-gray-50">
                <!--msg-->
              </div>
              <div class="h-2/6 bg-white rounded-r flex flex-col items-end">
                <el-input v-model="msg" type="textarea" resize="none" placeholder="请输入" maxlength="100"
                          show-word-limit class="h-full rounded"/>
                <el-button size="small" class="absolute right-3 bottom-2 w-1/4 mt-1">发送</el-button>
              </div>
            </div>
          </el-scrollbar>
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
