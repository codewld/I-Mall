<script setup lang="ts">
import { PropType, ref, Ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import IContainer from '@/components/iContainer';
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
   * 分页查询方法
   */
  pageFunction: {
    type: Function as PropType<CURD.pageFunction<unknown>>,
    required: true
  },
  /**
   * 是否需要搜索区
   */
  search: {
    type: Boolean,
    default: true
  }
})

const emits = defineEmits(['add', 'del', 'update', 'see'])


// -- 搜索相关 --
/**
 * 是否正在进行搜索
 */
const isSearching: Ref<boolean> = ref(false)

/**
 * 搜索参数
 */
const searchParam: Ref = ref({})

/**
 * 获取加工后的搜索参数
 */
const getValidSearchParam = () => {
  if (isSearching.value) {
    let validSearchParam: any = {}
    for (let key in searchParam.value) {
      if (searchParam.value[key] !== undefined && searchParam.value[key] !== null && searchParam.value[key] !== '') {
        validSearchParam[key] = searchParam.value[key]
      }
    }
    return validSearchParam
  }
  return null
}

/**
 * 开始搜索
 */
const doSearch = () => {
  isSearching.value = true
  doLoad()
}

/**
 * 重置搜索
 */
const resetSearch = () => {
  searchParam.value = {}
  isSearching.value = false
  doLoad()
}


// -- 数据加载相关 --
/**
 * 是否正在加载中
 */
const isLoading = ref(false)

/**
 * 分页参数
 */
const pageParam: Ref<CURD.pageParam> = ref({
  pageNum: 1,
  pageSize: 8
})

/**
 * 数据列表
 */
const dataList: Ref<CURD.dataList<unknown>> = ref({
  total: 0,
  list: []
})

/**
 * 分页查询
 */
const doLoad = () => {
  isLoading.value = true
  props.pageFunction(pageParam, getValidSearchParam()).then(res => {
    dataList.value = res
  }).catch(err => {
    ElMessage.warning(err)
  }).finally(() => {
    isLoading.value = false
  })
}

/**
 * 监听分页参数的改变，查询用户列表
 */
watch(
    pageParam,
    () => {
      doLoad()
    },
    { deep: true, immediate: true }
)


// -- 表格选中情况相关 --
/**
 * 当前被选中的行
 */
const currentRow = ref()

/**
 * 处理行的选中事件
 */
const handleCurrentChange = (val: undefined) => {
  currentRow.value = val
}


// -- CURD相关 --
/**
 * dialog是否可见
 */
const dialogVisible = ref(false)

/**
 * 表单数据
 */
let formData = ref()

/**
 * 当前正在进行的操作 [增、改、查]
 */
const actionType: Ref<string | undefined> = ref(undefined)

/**
 * 处理添加表单
 */
const handleAddForm = () => {
  formData.value = {}
  actionType.value = 'add'
  dialogVisible.value = true
}

/**
 * 执行添加
 */
const doAdd = () => {
  props.addFunction(formData).then(() => {
    ElMessage.success('操作成功')
    dialogVisible.value = false
    doLoad()
  }).catch(err => {
    ElMessage.warning(err)
  })
}

/**
 * 执行删除
 */
const doDel = () => {
  props.delFunction(currentRow.value.id).then(() => {
    ElMessage.success('操作成功')
    doLoad()
  }).catch(err => {
    ElMessage.warning(err)
  })
}

/**
 * 处理修改表单
 */
const handleUpdateForm = () => {
  formData.value = { ...currentRow.value }
  actionType.value = 'update'
  dialogVisible.value = true
}

/**
 * 执行修改
 */
const doUpdate = () => {
  let data: any = {}
  for (let key in formData.value) {
    if (formData.value[key] !== undefined && formData.value[key] !== null && formData.value[key] !== ''
        && formData.value[key] !== currentRow.value[key]) {
      data[key] = formData.value[key]
    }
  }
  if (Object.getOwnPropertyNames(data).length === 0) {
    ElMessage.warning('请修改')
    return
  }
  props.updateFunction(currentRow.value.id, data).then(() => {
    ElMessage.success('操作成功')
    dialogVisible.value = false
    doLoad()
  }).catch(err => {
    ElMessage.warning(err)
  })
}

/**
 * 处理查看表单
 */
const handleSeeForm = () => {
  formData.value = currentRow.value
  actionType.value = 'see'
  dialogVisible.value = true
}
</script>

<template>
  <i-container>
    <!--搜索区-->
    <el-card shadow="never" v-if="search">
      <template #header>
        <div class="flex justify-between">
          <p>搜索区</p>
          <el-button-group>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="primary" @click="doSearch">搜索</el-button>
          </el-button-group>
        </div>
      </template>
      <el-form :model="searchParam" inline>
        <template v-for="(field, key) in fieldList" :key="key">
          <el-form-item v-if="field?.searchConf?.display" :label="`${field.name}：`">
            <slot :name="`search-item-${field.code}`" :row="searchParam">
              <el-input v-model.trim="searchParam[field.code]" :placeholder="`请输入${field.name}`"/>
            </slot>
          </el-form-item>
        </template>
      </el-form>
    </el-card>
    <!--数据区-->
    <el-card shadow="never">
      <template #header>
        <div class="flex justify-between">
          <p>数据区</p>
          <el-button-group>
            <slot name="table-button-i-front"/>
            <el-button type="primary" @click="handleAddForm">添加</el-button>
            <el-popconfirm title="是否要进行删除？" @confirm="doDel">
              <template #reference>
                <el-button type="danger" @click="emits('del')" :disabled="!currentRow">删除</el-button>
              </template>
            </el-popconfirm>
            <el-button type="warning" @click="handleUpdateForm" :disabled="!currentRow">修改</el-button>
            <el-button type="success" @click="handleSeeForm" :disabled="!currentRow">查看</el-button>
            <slot name="table-button-i-rear"/>
          </el-button-group>
        </div>
      </template>
      <!--表格-->
      <el-table v-loading="isLoading" :data="dataList.list" stripe highlight-current-row
                @current-change="handleCurrentChange">
        <slot name="table-column-i-front"/>
        <template v-for="(field, key) in fieldList" :key="key">
          <el-table-column v-if="field.tableConf.display ?? true"
                           :prop="field.code" :label="field.name"
                           :width="field.tableConf.width ?? undefined"
                           :min-width="field.tableConf.minWidth ?? undefined"
                           :fixed="field.tableConf.fixed ?? undefined"
                           :align="field.tableConf.align ?? 'center'">
            <template v-if="$slots[`table-column-${field.code}`]" v-slot:default="scope">
              <slot :name="`table-column-${field.code}`" :row="scope.row"></slot>
            </template>
          </el-table-column>
        </template>
        <slot name="table-column-i-rear"/>
      </el-table>
      <!--分页-->
      <el-pagination :page-sizes="[4, 8, 16]" layout="total, sizes, prev, pager, next, jumper" :total="dataList.total"
                     v-model:current-page="pageParam.pageNum" v-model:page-size="pageParam.pageSize"
                     class="justify-center mt-5">
      </el-pagination>
    </el-card>
  </i-container>

  <!--增、改、查 对话框-->
  <el-dialog v-model="dialogVisible" title="Tips" width="50%">
    <el-form :model="formData" inline label-position="top" class="justify-between">
      <slot name="form-item-i-front"/>
      <template v-for="(field, key) in fieldList" :key="key">
        <el-form-item v-if="field?.formConf?.[actionType] ?? true" :label="`${field.name}：`" class="w-2/5 flex-grow">
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
