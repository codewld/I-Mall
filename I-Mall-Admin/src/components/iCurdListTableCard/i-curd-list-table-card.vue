<script setup lang="ts">
import useTableCurrentRow from '@/composables/curd/useTableCurrentRow';
import { PropType } from 'vue';
import { CURD } from '@/@types/curd';
import useList from '@/composables/curd/useList';

const props = defineProps({
  /**
   * 字段列表
   */
  fieldList: {
    type: Array as PropType<CURD.field[]>,
    required: true
  },
  /**
   * 批量查询方法
   */
  listFunction: {
    type: Function as PropType<CURD.listFunction<unknown>>,
    required: true
  }
})


// -- 表格选中行相关 --
const {
  currentRow,
  handleCurrentChange
} = useTableCurrentRow()


// -- 批量查询相关 --
const {
  listData,
  isLoading,
  doLoad
} = useList(props.listFunction);


defineExpose({
  currentRow,
  doLoad
})
</script>

<template>
  <el-card shadow="never">
    <!--标题及按钮-->
    <template #header>
      <div class="flex justify-between items-center">
        <p>数据区</p>
        <el-button-group>
          <slot name="button"></slot>
        </el-button-group>
      </div>
    </template>
    <!--表格-->
    <el-table v-loading="isLoading" :data="listData" stripe highlight-current-row row-key="id"
              @current-change="handleCurrentChange">
      <slot name="table"></slot>
    </el-table>
  </el-card>
</template>
