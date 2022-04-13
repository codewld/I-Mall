import { ref } from 'vue';

/**
 * 表格选中行
 * @param callBackFunction 行选中的回调方法
 */
export default function useTableCurrentRow(callBackFunction?: Function) {

  /**
   * 当前被选中的行
   */
  const currentRow = ref()

  /**
   * 处理行的选中事件
   */
  const handleCurrentChange = (val: any) => {
    currentRow.value = val
    callBackFunction && callBackFunction(val)
  }

  return {
    currentRow,
    handleCurrentChange
  }
}
