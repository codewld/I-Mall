import request from '@/api/request';
import { CURD } from '@/@types/curd';

const PREFIX = '/ums-admin'

/**
 * 添加方法
 */
export const rAdd: CURD.addFunction<Admin.adminParam> = (data) => {
  return request(PREFIX, 'post', data)
}

/**
 * 删除方法
 */
export const rDel: CURD.delFunction = (id) => {
  return request(`${ PREFIX }/${ id }`, 'delete')
}

/**
 * 修改方法
 */
export const rUpdate: CURD.updateFunction<Admin.adminParam> = (id, data) => {
  return request(`${ PREFIX }/${ id }`, 'put', data)
}

/**
 * 分页查询用户列表
 */
export const rList: CURD.listFunction<Admin.admin> = (data) => {
  return request(`${ PREFIX }/list`, 'get', data)
}
