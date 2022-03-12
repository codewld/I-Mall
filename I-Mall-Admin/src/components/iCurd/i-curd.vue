<script setup lang="ts">
import { PropType, Ref, ref } from 'vue';
import IContainer from '@/components/iContainer';
import { CURD } from '@/@types/curd';
import useFormCurd from '@/composables/curd/useFormCurd';
import ICurdSearchCard from '@/components/iCurdSearchCard/i-curd-search-card.vue';
import ICurdPageTableCard from '@/components/iCurdPageTableCard/i-curd-page-table-card.vue';
import ICurdListTableCard from '@/components/iCurdListTableCard/i-curd-list-table-card.vue';
import useTableCurrentRow from '@/composables/curd/useTableCurrentRow';

const props = defineProps({
  /**
   * 字段列表
   */
  fieldList: {
    type: Array as PropType<CURD.field[]>,
    required: true
  },
  /**
   * 新增方法
   */
  addFunction: {
    type: Function as PropType<CURD.addFunction<unknown>>,
    required: true
  },
  /**
   * 删除方法
   */
  delFunction: {
    type: Function as PropType<CURD.delFunction>,
    required: true
  },
  /**
   * 修改方法
   */
  updateFunction: {
    type: Function as PropType<CURD.updateFunction<unknown>>,
    required: true
  },
  /**
   * 是否分页
   */
  isPage: {
    type: Boolean,
    default: true
  },
  /**
   * 分页查询方法
   */
  pageFunction: {
    type: Function as PropType<CURD.pageFunction<unknown, unknown>>
  },
  /**
   * 批量查询方法
   */
  listFunction: {
    type: Function as PropType<CURD.listFunction<unknown, unknown>>
  },
  /**
   * 是否立即加载数据
   */
  isImmediate: {
    type: Boolean,
    default: true
  },
  /**
   * 是否有搜索框
   */
  hasSearch: {
    type: Boolean,
    default: true
  },
  /**
   * 按钮列表
   */
  buttonList: {
    type: Array as PropType<string[]>,
    default: () => ['add', 'del', 'update', 'see']
  },
  /**
   * 空数据显示文本
   */
  emptyText: {
    type: String
  }
})


const emits = defineEmits(['currentChange'])


// -- table相关 --
/**
 * table ref
 */
const table = ref()

/**
 * 表格数据加载
 */
const load = (val?: Ref<object>) => {
  table.value?.doLoad(val)
}


// -- table当前行相关 --
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


// -- 表单CURD相关 --
const {
  dialogVisible,
  formData,
  actionType,
  beforeAdd,
  doAdd,
  doDel,
  beforeUpdate,
  doUpdate,
  beforeSee
} = useFormCurd(load, props.fieldList, currentRow, props.addFunction, props.delFunction, props.updateFunction)


defineExpose({
  load
})
</script>

<template>
  <i-container>
    <!--搜索区-->
    <i-curd-search-card v-if="hasSearch" :field-list="fieldList" @load="load">
      <template v-slot:default="{searchParam}">
        <template v-for="(field, key) in fieldList" :key="key">
          <el-form-item v-if="field?.searchConf?.display" :label="`${field.name}：`">
            <slot :name="`search-item-${field.code}`" :row="searchParam">
              <el-input v-model.trim="searchParam[field.code]" :placeholder="`请输入${field.name}`"/>
            </slot>
          </el-form-item>
        </template>
      </template>
    </i-curd-search-card>

    <!--数据区-->
    <component :is="isPage ? ICurdPageTableCard : ICurdListTableCard"
               ref="table" :field-list="fieldList" :page-function="pageFunction" :list-function="listFunction"
               @current-change="handleCurrentChange" :is-immediate="isImmediate" :empty-text="emptyText">
      <template v-slot:button>
        <slot name="table-button-i-front" :currentRow="currentRow"/>
        <el-button v-if="buttonList.includes('add')" type="primary" @click="beforeAdd">添加</el-button>
        <el-popconfirm v-if="buttonList.includes('del')" title="是否要进行删除？" @confirm="doDel">
          <template #reference>
            <el-button type="danger" :disabled="!currentRow">删除</el-button>
          </template>
        </el-popconfirm>
        <el-button v-if="buttonList.includes('update')" type="warning" @click="beforeUpdate" :disabled="!currentRow">
          修改
        </el-button>
        <el-button v-if="buttonList.includes('see')" type="success" @click="beforeSee" :disabled="!currentRow">查看
        </el-button>
        <slot name="table-button-i-rear" :currentRow="currentRow"/>
      </template>
      <template v-slot:table>
        <slot name="table-column-i-front"/>
        <template v-for="(field, key) in fieldList" :key="key">
          <el-table-column v-if="field.tableConf?.display ?? true"
                           :prop="field.code" :label="field.name"
                           :width="field.tableConf?.width ?? undefined"
                           :min-width="field.tableConf?.minWidth ?? undefined"
                           :fixed="field.tableConf?.fixed ?? undefined"
                           :align="field.tableConf?.align ?? 'center'">
            <template v-if="$slots[`table-column-${field.code}`]" v-slot:default="scope">
              <slot :name="`table-column-${field.code}`" :row="scope.row"></slot>
            </template>
          </el-table-column>
        </template>
        <slot name="table-column-i-rear"/>
      </template>
    </component>
  </i-container>

  <!--增、改、查 对话框-->
  <el-dialog v-model="dialogVisible" width="50%" destroy-on-close>
    <el-form :model="formData" inline label-position="top" class="justify-between">
      <slot name="form-item-i-front"/>
      <template v-for="(field, key) in fieldList" :key="key">
        <el-form-item v-if="field?.formConf?.[actionType] ?? true" :label="`${field.name}：`" class="flex-grow"
                      :class="{'w-full': field?.formConf?.oneRow, 'w-2/5': !field?.formConf?.oneRow}">
          <slot :name="`form-item-${field.code}`" :row="formData" :disabled="actionType === 'see'">
            <el-input v-model.trim="formData[field.code]"
                      :disabled="actionType === 'see'"
                      :placeholder="actionType === 'see' ? '' : `请输入${field.name}`"/>
          </slot>
        </el-form-item>
      </template>
      <slot name="form-item-i-rear"/>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button v-if="actionType === 'add'" type="primary" @click="doAdd">确认</el-button>
      <el-button v-else-if="actionType === 'update'" type="primary" @click="doUpdate">确认</el-button>
      <el-button v-else type="primary" @click="dialogVisible = false">关闭</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
/*解决datePicker前后缀显示异常的问题*/
:deep(.el-input__prefix), :deep(.el-input__suffix) {
  display: block;
}
</style>
