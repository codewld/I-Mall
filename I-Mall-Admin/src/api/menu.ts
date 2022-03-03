import request from '@/api/request';
import { CURD } from '@/@types/curd';

const PREFIX = '/ums/menu'

/**
 * 添加
 */
export const rAdd: CURD.addFunction<Menu.menuParam> = (data) => {
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
export const rUpdate: CURD.updateFunction<Menu.menuParam> = (id, data) => {
  return request(`${ PREFIX }/${ id }`, 'put', data)
}

/**
 * 批量查询父节点下的子菜单
 */
export const rListSon = (id: number): Promise<Menu.menu[]> => {
  return request(`${ PREFIX }/list/${ id }`, 'get')
}

/**
 * 批量查询菜单 [标记形式、树形结构]
 */
export const rListMark = (): Promise<Menu.menuMark[]> => {
  return request(`${ PREFIX }/tree/mark`, 'get')
}
