<script setup lang="ts">
import {rAdd, rDel, rUpdate, rList} from '@/api/role';
import { getFormattedDateTime } from '@/utils/dateUtil';
import { Check, Close } from '@element-plus/icons-vue';
import { CURD } from '@/@types/curd';
import ICurd from '@/components/iCurd/i-curd.vue';

const fieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { width: 80, fixed: 'left' }, formConf: { add: false, update: false } },
  { code: 'name', name: '名称', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'status', name: '启用状态', tableConf: { width: 80 }, searchConf: { display: true } },
  { code: 'note', name: '备注', tableConf: { minWidth: 200 } },
  { code: 'createTime', name: '创建时间', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { display: false }, formConf: { add: false, update: false } }
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
    <!--自定义表单项-->
    <template v-slot:form-item-status="scope">
      <el-switch v-model="scope.row.status" :disabled="scope.disabled"/>
    </template>
    <template v-slot:form-item-createTime="scope">
      <el-date-picker v-model="scope.row.createTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
    <template v-slot:form-item-updateTime="scope">
      <el-date-picker v-model="scope.row.updateTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
  </i-curd>
</template>
