<script setup lang="ts">
import useTableCurrentRow from '@/composables/curd/useTableCurrentRow';
import usePage from '@/composables/curd/usePage';
import { PropType } from 'vue';
import { CURD } from '@/@types/curd';

const props = defineProps({
  /**
   * 字段列表
   */
  fieldList: {
    type: Array as PropType<CURD.field[]>,
    required: true
  },
  /**
   * 分页查询方法
   */
  pageFunction: {
    type: Function as PropType<CURD.pageFunction<unknown, unknown>>,
    required: true
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



// -- 分页查询相关 --
const {
  pageParam,
  pageData,
  isLoading,
  doLoad
} = usePage(props.pageFunction)


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
    <el-table v-loading="isLoading" :data="pageData.list" stripe highlight-current-row
              @current-change="handleCurrentChange">
      <slot name="table"></slot>
    </el-table>
    <!--分页-->
    <el-pagination :page-sizes="[2, 4, 8, 16]" layout="total, sizes, prev, pager, next, jumper" :total="pageData.total"
                   v-model:current-page="pageParam.pageNum" v-model:page-size="pageParam.pageSize"
                   class="justify-center mt-5">
    </el-pagination>
  </el-card>
</template>
