<script setup lang="ts">
import { rAdd, rDel, rUpdate, rPage, rListMenuId, rUpdateMenu } from '@/api/role';
import { getFormattedDateTime } from '@/utils/dateUtil';
import { CURD } from '@/@types/curd';
import ICurd from '@/components/iCurd/i-curd.vue';
import { Ref, ref } from 'vue';
import { ElMessage } from 'element-plus/es';
import IMenuPicker from '@/components/iMenuPicker';
import { isSame } from '@/utils/arrayUtil';

const fieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'code', name: '编码', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'name', name: '名称', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'note', name: '备注', tableConf: { minWidth: 200 }, formConf: { oneRow: true } },
  { code: 'createTime', name: '创建时间', tableConf: { width: 200 }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { width: 200 }, formConf: { add: false, update: false } }
]

// -- 菜单相关 --
/**
 * 对话框是否可见
 */
const menuDialogVisible = ref(false)

/**
 * 当前角色的ID
 */
const roleId: Ref<number> = ref(0)

/**
 * 菜单ID列表
 */
const menuIdList: Ref<number[]> = ref([])

/**
 * 未经处理的菜单ID列表
 */
const originMenuIdList: Ref<number[]> = ref([])

/**
 * 处理菜单对话框
 */
const handleMenuDialog = (id: number) => {
  rListMenuId(id).then(res => {
    roleId.value = id
    menuIdList.value = res
    originMenuIdList.value = [...res]
    menuDialogVisible.value = true
  }).catch(err => {
    ElMessage.warning(err)
  })
}

/**
 * 处理角色对应菜单的修改
 */
const handleUpdateMenu = () => {
  if (isSame(originMenuIdList.value, menuIdList.value)) {
    ElMessage.warning('请修改')
    return
  }
  rUpdateMenu(roleId.value, menuIdList).then(() => {
    ElMessage.success('操作成功')
    menuDialogVisible.value = false
  }).catch(err => {
    ElMessage.warning(err)
  })
}
</script>

<template>
  <i-curd :add-function="rAdd" :del-function="rDel" :update-function="rUpdate" :page-function="rPage"
          :field-list="fieldList">
    <!--自定义表格列-->
    <template v-slot:table-column-createTime="scope">
      {{ getFormattedDateTime(scope.row.createTime) }}
    </template>
    <template v-slot:table-column-updateTime="scope">
      {{ getFormattedDateTime(scope.row.updateTime) }}
    </template>
    <!--自定义表单项-->
    <template v-slot:form-item-createTime="scope">
      <el-date-picker v-model="scope.row.createTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
    <template v-slot:form-item-updateTime="scope">
      <el-date-picker v-model="scope.row.updateTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>

    <!--扩展对菜单的操作-->
    <template v-slot:table-button-i-rear="scope">
      <el-button color="#8e44ad" class="text-white"
                 @click="handleMenuDialog(scope.currentRow.id)" :disabled="!scope.currentRow">
        菜单
      </el-button>
    </template>
  </i-curd>

  <!--菜单信息对话框-->
  <el-dialog v-model="menuDialogVisible" title="菜单分配" width="30%">
    <i-menu-picker v-model:value="menuIdList" :multiple="true" :choose-non-leaf="false" :panel="true"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUpdateMenu">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
