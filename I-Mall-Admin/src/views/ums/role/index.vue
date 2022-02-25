<script setup lang="ts">
import {rAdd, rDel, rUpdate, rPage} from '@/api/role';
import { getFormattedDateTime } from '@/utils/dateUtil';
import { CURD } from '@/@types/curd';
import ICurd from '@/components/iCurd/i-curd.vue';

const fieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'name', name: '名称', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'note', name: '备注', tableConf: { minWidth: 200 } },
  { code: 'createTime', name: '创建时间', tableConf: { width: 200 }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { width: 200 }, formConf: { add: false, update: false } }
]
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
  </i-curd>
</template>
