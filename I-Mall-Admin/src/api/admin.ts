import request from '@/api/request';
import { CURD } from '@/@types/curd';
import { unref } from 'vue';

const PREFIX = '/ums-admin'

/**
 * 添加
 */
export const rAdd: CURD.addFunction<Admin.adminParam> = (data) => {
  return request(PREFIX, 'post', data)
}

/**
 * 删除
 */
export const rDel: CURD.delFunction = (id) => {
  return request(`${ PREFIX }/${ id }`, 'delete')
}

/**
 * 修改
 */
export const rUpdate: CURD.updateFunction<Admin.adminParam> = (id, data) => {
  return request(`${ PREFIX }/${ id }`, 'put', data)
}

/**
 * 列表查询
 */
export const rList: CURD.loadFunction<Admin.admin> = (pageParam, searchParam) => {
  return request(`${ PREFIX }/list`, 'get', {...unref(pageParam), ...unref(searchParam)})
}
