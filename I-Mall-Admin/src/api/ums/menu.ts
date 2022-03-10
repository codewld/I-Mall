import request from '@/api/request';
import useCurdApi from '@/composables/curd/useCurdApi';

const PREFIX = '/ums/menu'

export const { rAdd, rDel, rUpdate } = useCurdApi<Menu.menu, Menu.menuParam>(PREFIX)

/**
 * 查询树形结构的菜单
 */
export const rTree = (): Promise<Menu.menu[]> => {
  return request(`${ PREFIX }/tree`, 'get')
}

/**
 * 查询树形结构的菜单 [标记形式]
 */
export const rTreeMark = (): Promise<Menu.menuMark[]> => {
  return request(`${ PREFIX }/tree/mark`, 'get')
}
