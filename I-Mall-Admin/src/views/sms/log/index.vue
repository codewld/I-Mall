<script setup lang="ts">
import { rPage } from '@/api/sms/log';
import ICurd from '@/components/iCurd';
import { CURD } from '@/@types/curd/curd';
import { getFormattedDateTime } from '@/utils/dateUtil';
import IStatusIcon from '@/components/iStatusIcon';


/**
 * 字段信息列表
 */
const fieldList: CURD.field[] = [
  { code: 'summary', name: '接口概述', tableConf: { minWidth: 200 }, searchConf: { display: true } },
  { code: 'status', name: '执行状态', tableConf: { width: 100, fixed: 'right' }, searchConf: { display: true } },
  { code: 'uri', name: '请求接口', tableConf: { display: false }, formConf: { oneRow: true } },
  { code: 'method', name: '请求方法', tableConf: { display: false }, formConf: { see: false } },
  { code: 'username', name: '操作用户', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'ip', name: 'IP地址', tableConf: { width: 200 } },
  { code: 'parameter', name: '请求参数', tableConf: { display: false }, formConf: { oneRow: true } },
  { code: 'time', name: '操作时间', tableConf: { width: 200 } },
  { code: 'spendTime', name: '操作耗时', tableConf: { width: 200 } }
]
</script>

<template>
  <i-curd :field-list="fieldList" :page-function="rPage" :button-list="['see']">
    <!--自定义搜索-->
    <template v-slot:search-item-status="scope">
      <el-select v-model="scope.row.status" clearable placeholder="请选择">
        <el-option :value="true" label="成功"></el-option>
        <el-option :value="false" label="失败"></el-option>
      </el-select>
    </template>
    <!--自定义表格列-->
    <template v-slot:table-column-status="scope">
      <i-status-icon :status="scope.row.status"/>
    </template>
    <template v-slot:table-column-time="scope">
      {{ getFormattedDateTime(scope.row.time) }}
    </template>
    <!--自定义表单项-->
    <template v-slot:form-item-uri="scope">
      <el-input v-model="scope.row.uri" :disabled="scope.disabled">
        <template #prepend>
          {{ scope.row.method }}
        </template>
      </el-input>
    </template>
    <template v-slot:form-item-status="scope">
      <i-status-icon :status="scope.row.status"/>
    </template>
    <template v-slot:form-item-time="scope">
      <el-date-picker v-model="scope.row.time" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
  </i-curd>
</template>
