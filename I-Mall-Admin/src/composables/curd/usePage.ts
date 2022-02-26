import { Ref, ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { CURD } from '@/@types/curd';

/**
 * 分页查询数据
 * @param loadTrigger 加载扳机 [用于在组合式函数中调用加载方法]
 * @param pageFunction 分页查询方法
 * @param searchParam 搜索参数
 */
export default function usePage(loadTrigger: Ref<number>, pageFunction: CURD.pageFunction<unknown>, searchParam: Ref<object>) {

  /**
   * 分页参数
   */
  const pageParam: Ref<CURD.pageParam> = ref({
    pageNum: 1,
    pageSize: 8
  })

  /**
   * 分页数据
   */
  const dataList: Ref<CURD.dataList<unknown>> = ref({
    total: 0,
    list: []
  })

  /**
   * 是否正在加载中
   */
  const isLoading = ref(false)

  /**
   * 加载数据，分页查询
   */
  const doLoad = () => {
    isLoading.value = true
    pageFunction(pageParam, searchParam).then(res => {
      dataList.value = res
    }).catch(err => {
      ElMessage.warning(err)
    }).finally(() => {
      isLoading.value = false
    })
  }

  /**
   * 监听以查询
   */
  watch(
    [pageParam, loadTrigger],
    () => {
      doLoad()
    },
    { deep: true, immediate: true }
  )

  return {
    pageParam,
    dataList
  }

}
