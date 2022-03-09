<script setup lang="ts">
import { rListMark } from '@/api/ums/menu';
import { ElMessage } from 'element-plus/es';
import 'element-plus/es/components/message/style/css';
import { House, Document, Folder } from '@element-plus/icons-vue';
import { onMounted, ref } from 'vue';

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
  },
  /**
   * 是否添加根节点
   */
  addRoot: {
    type: Boolean,
    default: false
  },
  /**
   * 是否使用级联面板
   */
  panel: {
    type: Boolean,
    default: false
  }
});

const emits = defineEmits(['update:value']);

/**
 * 树形数据
 */
const data = ref()

onMounted(() => {
  rListMark().then(res => {
    const nodes = transform(res)
    if (props.addRoot) {
      nodes.unshift({
        value: 0,
        label: '无父级',
        disabled: false,
        children: null
      })
    }
    data.value = nodes
  }).catch(err => {
    ElMessage.warning(err)
  })
})

interface node {
  value: number,
  label: string,
  disabled: boolean,
  children: node[] | null
}

/**
 * 将menu标记转换为级联选择器所需的节点
 */
const transform = (list: Menu.menuMark[]): node[] => {
  return list.map(o => {
    return {
      value: o.id,
      label: o.name,
      disabled: !(props.chooseLeaf && !o.children || props.chooseNonLeaf && o.children),
      children: o.children ? transform(o.children) : null
    }
  })
}

/**
 * 级联选择器配置选项
 */
const cascaderProps = {
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
  multiple: props.multiple
}
</script>

<template>
  <el-cascader-panel v-if="panel"
             :model-value="value" @change="data => emits('update:value', data)"
             :disabled="disabled" :props="cascaderProps" :options="data">
    <template #default="{ node, data }">
      <div class="inline-flex items-center">
        <el-icon class="m-1.5">
          <House v-if="data.value === 0"/>
          <Folder v-else-if="data.children"/>
          <Document v-else/>
        </el-icon>
        {{ data.label }}
      </div>
    </template>
  </el-cascader-panel>
  <el-cascader v-else
                     :model-value="value" @change="data => emits('update:value', data)"
                     :disabled="disabled" :props="cascaderProps" :options="data">
    <template #default="{ node, data }">
      <div class="inline-flex items-center">
        <el-icon class="m-1.5">
          <House v-if="data.value === 0"/>
          <Folder v-else-if="data.children"/>
          <Document v-else/>
        </el-icon>
        {{ data.label }}
      </div>
    </template>
  </el-cascader>
</template>
