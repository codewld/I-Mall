<script setup lang="ts">
import { rListRootMark, rListSonMark } from '@/api/menu';
import { ElMessage } from 'element-plus/es';
import 'element-plus/es/components/message/style/css';
import { Document, Folder } from '@element-plus/icons-vue';

const props = defineProps({
  /**
   * 选择器绑定值
   */
  value: {
    required: true
  },
  /**
   * 是否禁用
   */
  disabled: {
    type: Boolean,
    default: false
  },
  /**
   * 是否多选
   */
  multiple: {
    type: Boolean,
    default: false
  },
  /**
   * 是否支持选择叶节点
   */
  chooseLeaf: {
    type: Boolean,
    default: true
  },
  /**
   * 是否支持选择非叶节点
   */
  chooseNonLeaf: {
    type: Boolean,
    default: true
  }
});

const emits = defineEmits(['update:value']);

/**
 * 级联选择器配置选项
 */
const cascaderProps = {
  /**
   * 懒加载
   */
  lazy: true,
  /**
   * 值仅包括节点，不包括节点路径
   */
  emitPath: false,
  /**
   * 可以选择任意一级的节点
   */
  checkStrictly: true,
  /**
   * 是否多选
   */
  multiple: props.multiple,
  async lazyLoad(node: any, resolve: (data: any[]) => void) {
    try {
      let marks
      if (node.root) {
        marks = await rListRootMark()
        marks.unshift({
          id: 0,
          name: '无父级',
          nonLeaf: false
        })
      } else {
        marks = await rListSonMark(node.value)
      }
      resolve(marks.map(o => {
            return {
              value: o.id,
              label: o.name,
              leaf: !o.nonLeaf,
              disabled: !(props.chooseLeaf && !o.nonLeaf || props.chooseNonLeaf && o.nonLeaf)
            }
          })
      )
    } catch (err) {
      ElMessage.warning(err as string)
    }
  }
}
</script>

<template>
  <el-cascader :model-value="value" @change="(data) => emits('update:value', data)" :disabled="disabled" :props="cascaderProps">
    <template #default="{ node, data }">
      <div class="inline-flex items-center">
        <el-icon class="m-1.5">
          <Folder v-if="!data.leaf"/>
          <Document v-else/>
        </el-icon>
        {{ data.label }}
      </div>
    </template>
  </el-cascader>
</template>
