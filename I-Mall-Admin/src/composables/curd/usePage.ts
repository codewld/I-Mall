import { Ref, ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { CURD } from '@/@types/curd';

/**
 * 分页查询
 * @param pageFunction 分页查询方法
 */
export default function usePage(pageFunction: CURD.pageFunction<unknown>) {

  /**
   * 搜索参数
   */
  const searchParam: Ref = ref({})

  /**
   * 重置搜索
   */
  const resetSearch = () => {
    searchParam.value = {}
    doLoad()
  }

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
  const doLoad = () => {
    isLoading.value = true
    pageFunction(pageParam, searchParam).then(res => {
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
      immediate: true
    }
  )

  return {
    searchParam,
    resetSearch,
    pageParam,
    pageData,
    isLoading,
    doLoad
  }

}
