<script setup lang="ts">
import { PropType, ref, Ref } from 'vue';
import { CURD } from '@/@types/curd';

const props = defineProps({
  /**
   * 字段列表
   */
  fieldList: {
    type: Array as PropType<CURD.field[]>,
    required: true
  }
})

const emit = defineEmits(['load'])

/**
 * 搜索参数
 */
const searchParam: Ref = ref({})

/**
 * 重置搜索
 */
const resetSearch = () => {
  searchParam.value = {}
  emit('load', searchParam.value)
}
</script>

<template>
  <el-card shadow="never">
    <!--标题及按钮-->
    <template #header>
      <div class="h-10 flex justify-between items-center">
        <p class="text-base">搜索区</p>
        <el-button-group>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="primary" @click="emit('load', searchParam)">搜索</el-button>
        </el-button-group>
      </div>
    </template>
    <!--搜索表单-->
    <el-form :model="searchParam" inline>
      <slot :searchParam="searchParam"></slot>
    </el-form>
  </el-card>
</template>
