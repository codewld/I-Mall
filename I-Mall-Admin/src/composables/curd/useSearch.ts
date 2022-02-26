import { ref, Ref } from 'vue';

/**
 * 搜索
 * @param loadTrigger 加载扳机 [用于在组合式函数中调用加载方法]
 */
export default function useSearch(loadTrigger: Ref<number>) {

  /**
   * 搜索参数
   */
  const searchParam: Ref = ref({})

  /**
   * 开始搜索
   */
  const doSearch = () => {
    loadTrigger.value++
  }

  /**
   * 重置搜索
   */
  const resetSearch = () => {
    searchParam.value = {}
    loadTrigger.value++
  }

  return {
    searchParam,
    doSearch,
    resetSearch
  }
}
