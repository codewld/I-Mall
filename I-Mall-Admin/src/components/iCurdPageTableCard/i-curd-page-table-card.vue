<script setup lang="ts">
import useTableCurrentRow from '@/composables/curd/useTableCurrentRow';
import usePage from '@/composables/curd/usePage';
import { PropType, watch } from 'vue';
import { CURD } from '@/@types/curd/curd';

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


const emits = defineEmits(['currentChange', 'load'])


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
  doLoad,
  clearData
} = usePage(props.pageFunction)


/**
 * 监听以要求父级查询
 */
watch(
    pageParam,
    () => {
      emits('load')
    },
    {
      deep: true,
      immediate: props.isImmediate
    }
)

defineExpose({
  currentRow,
  doLoad,
  clearData
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
              @current-change="handleCurrentChange" :empty-text="emptyText">
      <slot name="table"></slot>
    </el-table>
    <!--分页-->
    <el-pagination :page-sizes="[2, 4, 8, 16]" layout="total, sizes, prev, pager, next, jumper" :total="pageData.total"
                   v-model:current-page="pageParam.pageNum" v-model:page-size="pageParam.pageSize"
                   class="justify-center mt-5">
    </el-pagination>
  </el-card>
</template>
