<script setup lang="ts">
import { useRoute } from 'vue-router'
import { computed } from 'vue';
import { useRouterStore } from '@/store';
import ISideItem from '@/layouts/management/components/i-side-item.vue';
import { Fold, Expand } from '@element-plus/icons-vue';
import { useLayoutStore } from '@/store/modules/layout';

// -- 路由相关 --
// 当前路由
const route = useRoute()
const activeRoute = computed(() => route.path)

// 所有路由
const routerStore = useRouterStore();


// -- 侧边栏相关 --
const layoutStore = useLayoutStore()
</script>

<template>
  <el-menu router :default-active="activeRoute" class="h-full select-none" :collapse="layoutStore.isSideBarFold">
    <div class="h-8 flex justify-center items-center border-b">
      <el-icon :size="18" class="cursor-pointer" @click="layoutStore.setIsSideBarFold()">
        <Fold v-if="!layoutStore.isSideBarFold"></Fold>
        <Expand v-else></Expand>
      </el-icon>
    </div>
    <el-menu-item index="/home">首页</el-menu-item>
    <template v-for="(item, key) in routerStore.value" :key="key">
      <i-side-item :data="item"></i-side-item>
    </template>
  </el-menu>
</template>
