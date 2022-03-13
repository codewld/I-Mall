import { Ref, ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { CURD } from '@/@types/curd';

/**
 * 分页查询
 * @param pageFunction 分页查询方法
 * @param isImmediate 是否立即加载数据
 */
export default function usePage(pageFunction: CURD.pageFunction<unknown, unknown>, isImmediate: boolean) {

  /**
   * 分页参数
   */
  const pageParam: Ref<CURD.pageParam> = ref({
    pageNum: 1,
    pageSize: 8
  })

  /**
   * 分页查询数据列表
   */
  const pageData: Ref<CURD.pageData<unknown>> = ref({
    total: 0,
    list: []
  })

  /**
   * 是否正在加载中
   */
  const isLoading = ref(false)

  /**
   * 加载数据
   */
  const doLoad = (val?: Ref<object>) => {
    isLoading.value = true
    pageFunction(pageParam, val).then(res => {
      pageData.value = res
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
    pageParam,
    () => {
      doLoad()
    },
    {
      deep: true,
      immediate: isImmediate
    }
  )

  /**
   * 清空数据
   */
  const clearData = () => {
    pageData.value = {
      total: 0,
      list: []
    }
  }

  return {
    pageParam,
    pageData,
    isLoading,
    doLoad,
    clearData
  }

}
