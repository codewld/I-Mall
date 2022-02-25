import request from '@/api/request';
import { CURD } from '@/@types/curd';

const PREFIX = '/ums-menu'

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
 * 批量查询位于根结点的菜单
 */
export const rListRoot = (): Promise<Menu.menu[]> => {
  return request(`${ PREFIX }/list/root`, 'get')
}

/**
 * 批量查询父结点下的子菜单
 */
export const rListSon = (id: number): Promise<Menu.menu[]> => {
  return request(`${ PREFIX }/list/${ id }`, 'get')
}

/**
 * 批量查询位于根结点的菜单标记
 */
export const rListRootMark = (): Promise<Menu.menuMark[]> => {
  return request(`${ PREFIX }/list/mark/root`, 'get')
}

/**
 * 批量查询父结点下的子菜单标记
 */
export const rListSonMark = (id: number): Promise<Menu.menuMark[]> => {
  return request(`${ PREFIX }/list/mark/${ id }`, 'get')
}
