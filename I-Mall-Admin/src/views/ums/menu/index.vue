<script setup lang="ts">
import { rAdd, rDel, rUpdate, rTree } from '@/api/ums/menu';
import ICurd from '@/components/iCurd';
import { CURD } from '@/@types/curd/curd';
import { Document, Folder } from '@element-plus/icons-vue';
import { getFormattedDateTime } from '@/utils/dateUtil';
import IMenuPicker from '@/components/iMenuPicker';

const fieldList: CURD.field[] = [
  { code: 'id', name: 'id', tableConf: { display: false }, formConf: { add: false, update: false } },
  { code: 'parentId', name: '父级', tableConf: { display: false } },
  { code: 'name', name: '名称', tableConf: { width: 200, align: 'left' } },
  { code: 'code', name: '编码', tableConf: { width: 150 } },
  { code: 'sort', name: '排序', tableConf: { display: false } },
  { code: 'nonLeaf', name: '类型', tableConf: { display: false }, formConf: {addDefault: true} },
  { code: 'path', name: '路径', tableConf: { width: 400 }, formConf: { oneRow: true } },
  { code: 'component', name: '组件', tableConf: { width: 400 }, formConf: { oneRow: true } },
  { code: 'note', name: '备注', tableConf: { minWidth: 200 }, formConf: { oneRow: true } },
  { code: 'createTime', name: '创建时间', tableConf: { width: 200 }, formConf: { add: false, update: false } },
  { code: 'updateTime', name: '更新时间', tableConf: { width: 200 }, formConf: { add: false, update: false } }
]

/**
 * 处理表单中nonLeaf项的选择
 */
const handleNonLeafChoose = (val: boolean, row: any) => {
  if (val) {
    row.component = 'management'
  } else if (row.component === 'management') {
    row.component = null
  }
}
</script>

<template>
  <i-curd :add-function="rAdd" :del-function="rDel" :update-function="rUpdate" :is-page="false" :list-function="rTree"
          :field-list="fieldList" :has-search="false">
    <!--自定义表格列-->
    <template v-slot:table-column-name="scope">
      <div class="inline-flex items-center">
        <el-icon :size="20" class="m-1.5">
          <Folder v-if="scope.row.nonLeaf"/>
          <Document v-else/>
        </el-icon>
        {{ scope.row.name }}
      </div>
    </template>
    <template v-slot:table-column-path="scope">
      <span class="-m-1 text-gray-300">XXXXX.com/#</span>
      {{ scope.row.path }}
    </template>
    <template v-slot:table-column-component="scope">
      <template v-if="scope.row.nonLeaf">
        <p v-if="scope.row.component === 'management'">管理界面布局组件</p>
      </template>
      <template v-else>
        <span class="-m-1 text-gray-300">@/views</span>
        {{ scope.row.component }}
      </template>
    </template>
    <template v-slot:table-column-createTime="scope">
      {{ getFormattedDateTime(scope.row.createTime) }}
    </template>
    <template v-slot:table-column-updateTime="scope">
      {{ getFormattedDateTime(scope.row.updateTime) }}
    </template>
    <!--自定义表单项-->
    <template v-slot:form-item-parentId="scope">
      <i-menu-picker v-model:value="scope.row.parentId" :disabled="scope.disabled"
                     :choose-leaf="false" :add-root="true"/>
    </template>
    <template v-slot:form-item-sort="scope">
      <el-input-number v-model="scope.row.sort" :disabled="scope.disabled" :min="0" :max="20"/>
    </template>
    <template v-slot:form-item-nonLeaf="scope">
      <el-radio-group v-model="scope.row.nonLeaf" :disabled="scope.disabled" @change="handleNonLeafChoose($event, scope.row)">
        <el-radio-button :label="true">菜单</el-radio-button>
        <el-radio-button :label="false">页面</el-radio-button>
      </el-radio-group>
    </template>
    <template v-slot:form-item-path="scope">
      <el-input v-model.trim="scope.row.path" :disabled="scope.disabled"
                :placeholder="scope.disabled ? '' : '请输入路径'">
        <template v-slot:prepend>XXXXX.com/#</template>
      </el-input>
    </template>
    <template v-slot:form-item-component="scope">
      <el-input v-if="scope.row.nonLeaf" model-value="管理界面布局组件" :disabled="true">
      </el-input>
      <el-input v-else v-model.trim="scope.row.component" :disabled="scope.row.nonLeaf || scope.disabled"
                :placeholder="scope.row ? '' : '请输入组件'">
        <template v-slot:prepend>@/views</template>
      </el-input>
    </template>
    <template v-slot:form-item-createTime="scope">
      <el-date-picker v-model="scope.row.createTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
    <template v-slot:form-item-updateTime="scope">
      <el-date-picker v-model="scope.row.updateTime" type="datetime" :disabled="scope.disabled" placeholder="暂无"/>
    </template>
  </i-curd>
</template>
