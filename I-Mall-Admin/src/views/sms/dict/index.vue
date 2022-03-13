<script setup lang="ts">
import ICurd from '@/components/iCurd';
import {
  rAdd, rDel, rUpdate, rPage,
  rAddDetail, rDelDetail, rUpdateDetail, rListDetail
} from '@/api/sms/dict';
import { CURD } from '@/@types/curd';
import { getFormattedDateTime } from '@/utils/dateUtil';
import useTableCurrentRow from '@/composables/curd/useTableCurrentRow';
import { ref, watch } from 'vue';

// -- 字典CURD相关 --
/**
 * 字段信息列表
 */
const fieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'code', name: '编码', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'name', name: '名称', tableConf: { width: 200 }, searchConf: { display: true } },
  { code: 'note', name: '备注', tableConf: { minWidth: 200 }, formConf: { oneRow: true } },
  { code: 'createTime', name: '创建时间', tableConf: { width: 200 }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { width: 200 }, formConf: { add: false, update: false } }
]


// -- 字典表格选中行相关 --
const {
  currentRow,
  handleCurrentChange
} = useTableCurrentRow()


// -- 字典细节CURD相关 --
/**
 * 细节CURD ref
 */
const detailCurd = ref()

/**
 * 细节字段信息列表
 */
const detailFieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'dictId', name: 'dictId', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'label', name: '名', tableConf: { width: 200 }, formConf: { oneRow: true } },
  { code: 'value', name: '值', tableConf: { minWidth: 200 }, formConf: { oneRow: true } },
  { code: 'createTime', name: '创建时间', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { display: false }, formConf: { add: false, update: false } }
]

/**
 * 加工后添加方法
 */
const rProcessedAddDetail: CURD.addFunction<Dict.dictDetail> = (data) => {
  return rAddDetail(currentRow.value.id, data)
}

/**
 * 加工后批量查询加载方法
 */
const rProcessedListDetail: CURD.listFunction<Dict.dictDetail, Dict.dictDetailParam> = () => {
  return rListDetail(currentRow.value.id)
}

/**
 * 细节CURD加载
 */
watch(currentRow, (val) => {
  if (val) {
    detailCurd.value?.load()
  }
})
</script>

<template>
  <div class="w-full h-full flex absolute top-0 bottom-0 left-0 right-0">
    <div class="w-3/5 h-full relative">
      <i-curd :add-function="rAdd" :del-function="rDel" :update-function="rUpdate" :page-function="rPage"
              :field-list="fieldList" :button-list="['add', 'del', 'update']"
              @current-change="handleCurrentChange">
        <!--自定义表格列-->
        <template v-slot:table-column-createTime="scope">
          {{ getFormattedDateTime(scope.row.createTime) }}
        </template>
        <template v-slot:table-column-updateTime="scope">
          {{ getFormattedDateTime(scope.row.updateTime) }}
        </template>
        <!--自定义表单项-->
        <template v-slot:form-item-updateTime="scope">
          <el-date-picker v-model="scope.row.updateTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
        </template>
        <template v-slot:form-item-loginTime="scope">
          <el-date-picker v-model="scope.row.loginTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
        </template>
      </i-curd>
    </div>
    <div class="w-2/5 h-full relative">
      <i-curd ref="detailCurd"
              :add-function="rProcessedAddDetail"
              :del-function="rDelDetail"
              :update-function="rUpdateDetail"
              :is-page="false"
              :list-function="rProcessedListDetail"
              :field-list="detailFieldList"
              :has-search="false"
              :button-list="currentRow ? ['add', 'del', 'update'] : []"
              :is-immediate="false"
              :empty-text="currentRow ? '暂无数据' : '请从左侧选择字典'">
      </i-curd>
    </div>
  </div>
</template>
