import request from '@/api/request';
import { Ref } from 'vue';
import useCurdApi from '@/composables/curd/useCurdApi';

const PREFIX = '/ums/role'

export const { rAdd, rDel, rUpdate, rPage } = useCurdApi<Role.role, Role.roleParam>(PREFIX)

/**
 * 批量查询角色 [标记列表]
 */
export const rListMark = (): Promise<Role.roleMark[]> => {
  return request(`${ PREFIX }/list/mark`, 'get')
}

/**
 * 修改角色拥有的菜单
 */
export const rUpdateMenu = (id: number, menuIdList: Ref<number[]>): Promise<string> => {
  return request(`${ PREFIX }/menu/${ id }`, 'put', menuIdList)
}

/**
 * 批量查询角色拥有的菜单 [ID列表]
 */
export const rListMenuId = (id: number): Promise<number[]> => {
  return request(`${ PREFIX }/menu/id/${ id }`, 'get')
}
