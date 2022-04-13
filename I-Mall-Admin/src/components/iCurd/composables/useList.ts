import { Ref, ref } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { CURD } from '@/@types/curd/curd';


/**
 * 批量查询
 * @param listFunction 批量查询方法
 */
export default function useList(listFunction: CURD.listFunction<unknown, unknown>) {

  /**
   * 批量查询数据列表
   */
  const listData: Ref<CURD.listData<unknown>> = ref([])

  /**
   * 是否正在加载中
   */
  const isLoading = ref(false)

  /**
   * 加载数据
   */
  const doLoad = (val?: Ref<object>) => {
    isLoading.value = true
    listFunction(val).then(res => {
      listData.value = res
    }).catch(err => {
      ElMessage.warning(err)
    }).finally(() => {
      isLoading.value = false
    })
  }

  /**
   * 清空数据
   */
  const clearData = () => {
    listData.value = []
  }

  return {
    listData,
    isLoading,
    doLoad,
    clearData
  }

}
