<script setup lang="ts">
import { onMounted, ref, Ref } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { Document, Folder } from '@element-plus/icons-vue';
import IContainer from '@/components/iContainer';
import { rAdd, rDel, rUpdate, rListSon } from '@/api/menu';
import { getFormattedDateTime } from '@/utils/dateUtil';
import useTableCurrentRow from '@/composables/curd/useTableCurrentRow';
import { getChange } from '@/utils/objUtil';
import IMenuPicker from '@/components/iMenuPicker';


// -- 数据加载相关 --
/**
 * 是否正在加载中
 */
const isLoading = ref(false)

/**
 * 数据列表
 */
const dataList: Ref<Menu.menu[]> = ref([])

/**
 * 加载数据，批量查询位于根结点的菜单
 */
const listRoot = () => {
  isLoading.value = true
  rListSon(0).then(res => {
    dataList.value = res
  }).catch(err => {
    ElMessage.warning(err)
  }).finally(() => {
    isLoading.value = false
    table.value.setCurrentRow(undefined)
    currentRow.value = undefined
  })
}

onMounted(() => {
  listRoot()
})

/**
 * 批量查询父结点下的子菜单
 */
const listSon = (tree: Menu.menu, treeNode: unknown, resolve: (data: Menu.menu[]) => void) => {
  rListSon(tree.id).then(res => {
    resolve(res)
  }).catch(err => {
    ElMessage.warning(err)
  })
}


// -- 表格选中行相关 --
const { currentRow, handleCurrentChange } = useTableCurrentRow()


// -- 表格相关 --
/**
 * 表格ref
 */
const table = ref()

/**
 * 重置表格  以解决数据修改后不同步的问题
 */
const resetTable = (parentId: number) => {
  if (parentId === 0) {
    listRoot()
  } else {
    table.value.store.states.treeData.value[parentId].loaded = false
    table.value.store.states.treeData.value[parentId].expanded = false
    table.value.store.states.lazyTreeNodeMap.value[parentId] = []
    table.value.setCurrentRow(null)
  }
}


// -- 表单CURD相关 --
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
 * 准备添加
 */
const beforeAdd = () => {
  formData.value = {
    nonLeaf: true
  }
  actionType.value = 'add'
  dialogVisible.value = true
}

/**
 * 执行添加
 */
const doAdd = () => {
  rAdd(formData.value).then(() => {
    ElMessage.success('操作成功')
    dialogVisible.value = false
    resetTable(formData.value.parentId)
  }).catch(err => {
    ElMessage.warning(err)
  })
}

/**
 * 执行删除
 */
const doDel = () => {
  rDel(currentRow.value.id).then(() => {
    ElMessage.success('操作成功')
    resetTable(currentRow.value.parentId)
  }).catch(err => {
    ElMessage.warning(err)
  })
}

/**
 * 准备修改
 */
const beforeUpdate = () => {
  formData.value = { ...currentRow.value }
  actionType.value = 'update'
  dialogVisible.value = true
}

/**
 * 执行修改
 */
const doUpdate = () => {
  const data = getChange(formData.value, currentRow.value)
  if (Object.getOwnPropertyNames(data).length === 0) {
    ElMessage.warning('请修改')
    return
  }
  rUpdate(currentRow.value.id, data).then(() => {
    ElMessage.success('操作成功')
    dialogVisible.value = false
    resetTable(currentRow.value.parentId)
    resetTable(formData.value.parentId)
  }).catch(err => {
    ElMessage.warning(err)
  })
}

/**
 * 准备查看
 */
const beforeSee = () => {
  formData.value = currentRow.value
  actionType.value = 'see'
  dialogVisible.value = true
}


// -- 表单项相关 --
/**
 * 处理是否为非叶结点的选择
 */
const handelNonLeafChoose = (nonLeaf: boolean) => {
  if (nonLeaf) {
    formData.value.component = null
    formData.value.path = null
  }
}
</script>

<template>
  <i-container>
    <!--数据区-->
    <el-card shadow="never">
      <template #header>
        <div class="flex justify-between items-center">
          <p>数据区</p>
          <el-button-group>
            <el-button type="primary" @click="beforeAdd">添加</el-button>
            <el-popconfirm title="是否要进行删除？" @confirm="doDel">
              <template #reference>
                <el-button type="danger" :disabled="!currentRow">删除</el-button>
              </template>
            </el-popconfirm>
            <el-button type="warning" @click="beforeUpdate" :disabled="!currentRow">修改</el-button>
            <el-button type="success" @click="beforeSee" :disabled="!currentRow">查看</el-button>
          </el-button-group>
        </div>
      </template>
      <!--表格-->
      <el-table ref="table" v-loading="isLoading" :data="dataList" stripe highlight-current-row
                @current-change="handleCurrentChange"
                lazy :load="listSon" :tree-props="{children: 'children', hasChildren: 'nonLeaf'}" row-key="id">
        <el-table-column prop="name" label="名称" :width="200" label-class-name="text-center">
          <template v-slot:default="scope">
            <div class="inline-flex items-center">
              <el-icon :size="20" class="m-1.5">
                <Folder v-if="scope.row.nonLeaf"/>
                <Document v-else/>
              </el-icon>
              {{ scope.row.name }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="code" label="编码" :width="200" align="center"></el-table-column>
        <el-table-column prop="component" label="组件" :width="200" align="center">
          <template v-slot:default="scope">
            <template v-if="scope.row.path">
              <span class="text-gray-300">@/views/</span>
              {{ scope.row.component }}
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路径" :width="200" align="center">
          <template v-slot:default="scope">
            <template v-if="scope.row.path">
              <span class="text-gray-300">IP:端口/#/</span>
              {{ scope.row.path }}
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="note" label="备注" :min-width="200" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" :width="200" align="center">
          <template v-slot:default="scope">
            {{ getFormattedDateTime(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" :width="200" align="center">
          <template v-slot:default="scope">
            {{ getFormattedDateTime(scope.row.updateTime) }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </i-container>

  <!--增、改、查 对话框-->
  <el-dialog v-model="dialogVisible" width="50%" destroy-on-close>
    <el-form :model="formData" inline label-position="top" class="justify-between">
      <el-form-item v-if="actionType === 'see'" label="id：" class="w-2/5 flex-grow">
        <el-input v-model.trim="formData.id" :disabled="actionType === 'see'"/>
      </el-form-item>
      <el-form-item label="父级：" class="w-2/5 flex-grow">
        <i-menu-picker v-model:value="formData.parentId"
                       :disabled="actionType === 'see'" :choose-leaf="false" :add-root="true"/>
      </el-form-item>
      <el-form-item label="编码：" class="w-2/5 flex-grow">
        <el-input v-model.trim="formData.code" :disabled="actionType === 'see'"
                  :placeholder="actionType === 'see' ? '' : '请输入编码'"/>
      </el-form-item>
      <el-form-item label="名称：" class="w-2/5 flex-grow">
        <el-input v-model.trim="formData.name" :disabled="actionType === 'see'"
                  :placeholder="actionType === 'see' ? '' : '请输入名称'"/>
      </el-form-item>
      <el-form-item label="排序：" class="w-2/5 flex-grow">
        <el-input-number v-model="formData.sort" :disabled="actionType === 'see'" :min="0" :max="20"/>
      </el-form-item>
      <el-form-item label="类型：" class="w-full">
        <el-radio-group v-model="formData.nonLeaf" :disabled="actionType === 'see'"
                        @change="handelNonLeafChoose">
          <el-radio-button :label="true">菜单</el-radio-button>
          <el-radio-button :label="false">页面</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <template v-if="!formData.nonLeaf">
        <el-form-item label="组件：" class="w-2/5 flex-grow">
          <el-input v-model.trim="formData.component" :disabled="actionType === 'see'"
                    :placeholder="actionType === 'see' ? '' : '请输入组件'">
            <template v-slot:prepend>
              @/views/
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="路径：" class="w-2/5 flex-grow">
          <el-input v-model.trim="formData.path" :disabled="actionType === 'see'"
                    :placeholder="actionType === 'see' ? '' : '请输入路径'">
            <template v-slot:prepend>IP:端口/#/</template>
          </el-input>
        </el-form-item>
      </template>
      <el-form-item label="备注：" class="w-full">
        <el-input v-model.trim="formData.note" :disabled="actionType === 'see'"
                  :placeholder="actionType === 'see' ? '' : '请输入备注'"/>
      </el-form-item>
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
:deep(.el-table_1_column_1 .cell) {
  display: flex;
  align-items: center;
}
</style>

