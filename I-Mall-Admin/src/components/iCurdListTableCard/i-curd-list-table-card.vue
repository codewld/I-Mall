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
    type: Function as PropType<CURD.listFunction<unknown, unknown>>,
    required: true
  },
  /**
   * 是否立即加载数据
   */
  isImmediate: {
    type: Boolean,
    default: true
  },
  /**
   * 空数据显示文本
   */
  emptyText: {
    type: String
  }
})


const emits = defineEmits(['currentChange'])


// -- 表格选中行相关 --
/**
 * 向上传递当前行的改变事件
 */
const emitCurrentChange = (val: unknown) => {
  emits('currentChange', val)
}

const {
  currentRow,
  handleCurrentChange
} = useTableCurrentRow(emitCurrentChange)


// -- 批量查询相关 --
const {
  listData,
  isLoading,
  doLoad
} = useList(props.listFunction, props.isImmediate);


defineExpose({
  currentRow,
  doLoad
})
</script>

<template>
  <el-card shadow="never">
    <!--标题及按钮-->
    <template #header>
      <div class="h-10 flex justify-between items-center">
        <p class="text-base">数据区</p>
        <el-button-group>
          <slot name="button"></slot>
        </el-button-group>
      </div>
    </template>
    <!--表格-->
    <el-table v-loading="isLoading" :data="listData" stripe highlight-current-row row-key="id"
              @current-change="handleCurrentChange" :empty-text="emptyText">
      <slot name="table"></slot>
    </el-table>
  </el-card>
</template>
