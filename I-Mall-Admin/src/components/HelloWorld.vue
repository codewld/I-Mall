<script setup lang="ts">
import {ref} from "vue";

let msg = ref("Hello World!")
</script>

<template>
  <h1>{{ msg }}</h1>
  <el-button type="primary">Primary</el-button>
</template>
