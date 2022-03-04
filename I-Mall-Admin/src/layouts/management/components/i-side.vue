<script setup lang="ts">
import { useRoute } from 'vue-router'
import { computed } from 'vue';
import { useRouterStore } from '@/store';
import ISideItem from '@/layouts/management/components/i-side-item.vue';

// 当前路由
const route = useRoute()
const activeRoute = computed(() => route.path)

const routerStore = useRouterStore();
</script>

<template>
  <el-menu router :default-active="activeRoute" class="h-full select-none">
    <el-menu-item index="/home">首页</el-menu-item>
    <template v-for="(item, key) in routerStore.value" :key="key">
      <i-side-item :data="item"></i-side-item>
    </template>
  </el-menu>
</template>
