<script setup lang="ts">
import IHeader from './components/i-header.vue';
import ISide from './components/i-side.vue';
import { useLayoutStore } from '@/store/modules/layout';
import { computed } from 'vue';


// -- 侧边栏相关 --
const layoutStore = useLayoutStore()

const aSideWidth = computed(() => layoutStore.isSideBarFold ? '64px' : '200px');
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
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
