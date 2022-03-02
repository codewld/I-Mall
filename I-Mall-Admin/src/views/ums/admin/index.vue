<script setup lang="ts">
import { rAdd, rDel, rUpdate, rPage, rUpdateRole, rListRoleMark } from '@/api/admin';
import { rListMark } from '@/api/role';
import { getFormattedDateTime } from '@/utils/dateUtil';
import ICurd from '@/components/iCurd';
import IStatusIcon from '@/components/iStatusIcon';
import { CURD } from '@/@types/curd';
import { Ref, ref } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { isSame } from '@/utils/arrayUtil';

const fieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'username', name: '用户名', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'password', name: '密码', tableConf: { display: false }, formConf: { see: false } },
  { code: 'nickName', name: '昵称', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'email', name: '邮箱', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'status', name: '启用状态', tableConf: { width: 80 }, formConf: {addDefault: false}, searchConf: { display: true } },
  { code: 'note', name: '备注', tableConf: { minWidth: 200 } },
  { code: 'createTime', name: '创建时间', tableConf: { width: 200 }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { width: 200 }, formConf: { add: false, update: false } },
  { code: 'loginTime', name: '最后登录时间', tableConf: { width: 200 }, formConf: { add: false, update: false } }
]

// -- 角色相关 --
/**
 * 对话框是否可见
 */
const roleDialogVisible = ref(false)

/**
 * 所有角色列表
 */
const allRoleList: Ref<Role.roleMark[]> = ref([])

/**
 * 当前用户的ID
 */
const adminId: Ref<number> = ref(0)

/**
 * 用户对应角色ID列表
 */
const roleIdList: Ref<number[]> = ref([])

/**
 * 未经修改的用户对应角色ID列表
 */
const originRoleIdList: Ref<number[]> = ref([])

/**
 * 处理角色对话框
 */
const handleRoleDialog = (id: number) => {
  Promise.all([rListMark(), rListRoleMark(id)])
      .then(res => {
        allRoleList.value = res[0]
        adminId.value = id
        roleIdList.value = res[1] ? Array.from(res[1], item => item.id) : []
        originRoleIdList.value = [...roleIdList.value]
        roleDialogVisible.value = true
      })
      .catch(err => {
        ElMessage.warning(err)
      })
}

/**
 * 处理用户对应角色的修改
 */
const handleUpdateRole = () => {
  if (isSame(originRoleIdList.value, roleIdList.value)) {
    ElMessage.warning('请修改')
    return
  }
  rUpdateRole(adminId.value, roleIdList.value).then(() => {
    ElMessage.success('修改成功')
    roleDialogVisible.value = false
  }).catch(err => {
    ElMessage.warning(err)
  })
}
</script>

<template>
  <i-curd :add-function="rAdd" :del-function="rDel" :update-function="rUpdate" :page-function="rPage"
          :field-list="fieldList">
    <!--自定义搜索-->
    <template v-slot:search-item-status="scope">
      <el-select v-model="scope.row.status" clearable placeholder="请选择">
        <el-option :value="true" label="启用"></el-option>
        <el-option :value="false" label="禁用"></el-option>
      </el-select>
    </template>
    <!--自定义表格列-->
    <template v-slot:table-column-status="scope">
      <i-status-icon :status="scope.row.status"></i-status-icon>
    </template>
    <template v-slot:table-column-createTime="scope">
      {{ getFormattedDateTime(scope.row.createTime) }}
    </template>
    <template v-slot:table-column-updateTime="scope">
      {{ getFormattedDateTime(scope.row.updateTime) }}
    </template>
    <template v-slot:table-column-loginTime="scope">
      {{ getFormattedDateTime(scope.row.loginTime) }}
    </template>
    <!--自定义表单项-->
    <template v-slot:form-item-password="scope">
      <el-input v-model.trim="scope.row.password" :disabled="scope.disabled" placeholder="请输入新密码"/>
    </template>
    <template v-slot:form-item-status="scope">
      <el-switch v-model="scope.row.status" :disabled="scope.disabled"/>
    </template>
    <template v-slot:form-item-createTime="scope">
      <el-date-picker v-model="scope.row.createTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
    <template v-slot:form-item-updateTime="scope">
      <el-date-picker v-model="scope.row.updateTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
    <template v-slot:form-item-loginTime="scope">
      <el-date-picker v-model="scope.row.loginTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>

    <!--扩展对角色的操作-->
    <template v-slot:table-button-i-rear="scope">
      <el-button color="#8e44ad" class="text-white"
                 @click="handleRoleDialog(scope.currentRow.id)" :disabled="!scope.currentRow">
        角色
      </el-button>
    </template>
  </i-curd>

  <!--角色信息对话框-->
  <el-dialog v-model="roleDialogVisible" title="角色分配" width="30%">
    <el-checkbox-group v-model="roleIdList" class="flex flex-col">
      <el-checkbox v-for="(item, key) in allRoleList" :key="key" :label="item.id">{{ item.name }}</el-checkbox>
    </el-checkbox-group>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="roleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUpdateRole">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
