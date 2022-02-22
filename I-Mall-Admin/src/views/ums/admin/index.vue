<script setup lang="ts">
import { rAdd, rDel, rUpdate, rList } from '@/api/admin';
import { getFormattedDateTime } from '@/utils/dateUtil';
import { Check, Close } from '@element-plus/icons-vue';
import ICurd from '@/components/iCurd';
import { CURD } from '@/@types/curd';

const fieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { width: 80, fixed: 'left' }, formConf: { add: false, update: false } },
  { code: 'username', name: '用户名', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'password', name: '密码', tableConf: { display: false }, formConf: { see: false } },
  { code: 'email', name: '邮箱', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'nickName', name: '昵称', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'createTime', name: '创建时间', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'loginTime', name: '最后登录时间', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'status', name: '启用状态', tableConf: { width: 80 }, searchConf: { display: true } },
  { code: 'note', name: '备注', tableConf: { minWidth: 200 } }
]
</script>

<template>
  <i-curd :add-function="rAdd" :del-function="rDel" :update-function="rUpdate" :load-function="rList"
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
      <div class="flex items-center justify-center">
        <el-icon :size="20" :color="scope.row.status ? 'green' : 'red'">
          <Check v-if="scope.row.status"/>
          <Close v-else/>
        </el-icon>
      </div>
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
  </i-curd>
</template>
