<script setup lang="ts">

import { PropType } from 'vue';

const props = defineProps({
  data: {
    type: Object as PropType<Account.router>,
    required: true
  }
});
</script>

<template>
  <template v-if="data.children !== undefined">
    <el-sub-menu :index="data.path">
      <template v-slot:title>
        {{ data.name }}
      </template>
      <template v-for="(item, key) in data.children" :key="key">
        <i-side-item :data="item"></i-side-item>
      </template>
    </el-sub-menu>
  </template>
  <template v-else>
    <el-menu-item :index="data.path">
      {{ data.name }}
    </el-menu-item>
  </template>
</template>
