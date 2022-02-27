import request from '@/api/request';
import { CURD } from '@/@types/curd';
import { Ref, unref } from 'vue';

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
 * 分页查询
 */
export const rPage: CURD.pageFunction<Role.role> = (pageParam, searchParam) => {
  return request(`${ PREFIX }/page`, 'get', { ...unref(pageParam), ...unref(searchParam) })
}

/**
 * 批量查询角色标记
 */
export const rListMark = (): Promise<Role.roleMark[]> => {
  return request(`${ PREFIX }/list/mark`, 'get')
}

/**
 * 修改角色的菜单
 */
export const rUpdateMenu = (id: number, menuIdList: Ref<number[]>): Promise<string> => {
  return request(`${ PREFIX }/menu/${ id }`, 'put', menuIdList)
}

/**
 * 批量查询角色的菜单ID列表
 */
export const rListMenuId = (id: number): Promise<number[]> => {
  return request(`${ PREFIX }/menu/${ id }`, 'get')
}
