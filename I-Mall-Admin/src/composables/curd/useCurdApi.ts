import { CURD } from '@/@types/curd';
import request from '@/api/request';
import { unref } from 'vue';

/**
 * CURD Api生成方法
 */
export default function useCurdApi<T, P>(PREFIX: string) {
  /**
   * 添加
   */
  const rAdd: CURD.addFunction<P> = (data) => {
    return request(PREFIX, 'post', data)
  }

  /**
   * 删除
   */
  const rDel: CURD.delFunction = (id) => {
    return request(`${ PREFIX }/${ id }`, 'delete')
  }

  /**
   * 修改
   */
  const rUpdate: CURD.updateFunction<P> = (id, data) => {
    return request(`${ PREFIX }/${ id }`, 'put', data)
  }

  /**
   * 分页查询
   */
  const rPage: CURD.pageFunction<T, P> = (pageParam, searchParam) => {
    let data = { ...unref(pageParam), ...unref(searchParam) }
    return request(`${ PREFIX }/page`, 'get', data)
  }

  /**
   * 批量查询
   */
  const rList: CURD.listFunction<T, P> = (searchParam) => {
    return request(`${ PREFIX }/list`, 'get', searchParam)
  }

  return {
    rAdd,
    rDel,
    rUpdate,
    rPage,
    rList
  }

}
