<script setup lang="ts">
import { ElSubMenu } from 'element-plus';
import { useRoute } from 'vue-router'
import { computed } from 'vue';

const route = useRoute()
const activeRoute = computed(() => route.path)
</script>

<template>
  <el-menu router :default-active="activeRoute" class="h-full select-none">
    <el-menu-item index="/home">
      首页
    </el-menu-item>
    <el-sub-menu index="/ums">
      <template v-slot:title>
        后台管理
      </template>
      <el-menu-item index="/ums/admin">管理员管理</el-menu-item>
      <el-menu-item index="/ums/role">角色管理</el-menu-item>
      <el-menu-item index="/ums/menu">菜单管理</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>
