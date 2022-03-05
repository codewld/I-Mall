import { ref } from 'vue';

/**
 * 表格选中行
 */
export default function useTableCurrentRow() {

  /**
   * 当前被选中的行
   */
  const currentRow = ref()

  /**
   * 处理行的选中事件
   */
  const handleCurrentChange = (val: any) => {
    currentRow.value = val
  }

  return {
    currentRow,
    handleCurrentChange
  }
}
