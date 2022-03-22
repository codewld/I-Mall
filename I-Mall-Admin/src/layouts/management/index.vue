<script setup lang="ts">
import IHeader from './components/i-header.vue';
import ISide from './components/i-side.vue';
import IChat from '@/components/iChat/i-chat.vue';
import { useLayoutStore } from '@/store/modules/layout';
import { computed, onMounted } from 'vue';


// -- 侧边栏相关 --
const layoutStore = useLayoutStore()

const aSideWidth = computed(() => layoutStore.isSideBarFold ? '64px' : '200px')

onMounted(() => {
  // 记录初始宽度
  let width = document.body.offsetWidth

  // 根据初始宽度设置侧边栏是否收缩
  layoutStore.setIsSideBarFold(width < 1000)
})
</script>

<template>
  <el-container class="h-full">
    <el-header class="p-0">
      <i-header/>
    </el-header>
    <el-container>
      <el-aside :width="aSideWidth" class="flex flex-col">
        <i-side/>
      </el-aside>
      <el-main class="relative">
        <i-chat class="fixed right-10 bottom-10"/>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
