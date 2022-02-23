import request from '@/api/request';
import { CURD } from '@/@types/curd';
import { unref } from 'vue';

const PREFIX = '/ums-role'

/**
 * 添加
 */
export const rAdd: CURD.addFunction<Role.roleParam> = (data) => {
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
export const rUpdate: CURD.updateFunction<Role.roleParam> = (id, data) => {
  return request(`${ PREFIX }/${ id }`, 'put', data)
}

/**
 * 列表查询
 */
export const rList: CURD.loadFunction<Role.role> = (pageParam, searchParam) => {
  return request(`${ PREFIX }/list`, 'get', {...unref(pageParam), ...unref(searchParam)})
}
