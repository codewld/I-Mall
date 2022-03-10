import request from '@/api/request';
import useCurdApi from '@/composables/curd/useCurdApi';

const PREFIX = '/ums/admin'

export const { rAdd, rDel, rUpdate, rPage } = useCurdApi<Admin.admin, Admin.adminParam>(PREFIX)

/**
 * 修改用户拥有的角色
 */
export const rUpdateRole = (id: number, roleIdList: number[]): Promise<string> => {
  return request(`${ PREFIX }/role/${ id }`, 'put', roleIdList)
}

/**
 * 批量查询用户拥有的角色 [标记列表]
 */
export const rListRoleMark = (id: number): Promise<Role.roleMark[]> => {
  return request(`${ PREFIX }/role/mark/${ id }`, 'get')
}
